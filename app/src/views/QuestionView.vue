<template>
  <div class="container">

    <div class="questionBox">
      <span id="title" style="color: black">{{question.title}}</span>
      <br>
      <VoteChoice  v-bind:id="question.id" v-bind:score="question.score" v-bind:type="'question'" v-bind:canVote="true"/>
      <span id="qText" v-html="question.text"></span></div>
      <div class = "sorting">
          Sort by:
          <select>
              <option selected>Time</option>
              <option>Score</option>
          </select>
      </div>

      <div class = "answerBox" v-for="answer in answers" v-bind:key="answer.id" >
      <VoteChoice v-bind:id="answer.id" v-bind:type="'post'" v-bind:score="answer.score" v-bind:canVote="true"/>
      <span v-html="answer.text"></span>
      <div class = "comment" v-for="comment in answer.comments" v-bind:key="comment.id">
      <VoteChoice  v-bind:id="comment.id" v-bind:type="comment"/>{{comment.text}}</div>
    <div class = "commentButton">
      <button type="button" @click = "postComment(answer)">Comment</button>
    </div>
    </div>
    <div class="post_section">
    <form @submit.prevent="addAnswer" >
      <textarea v-model="text" name = "text" style = "width: 80%; height: 118px;" placeholder="Insert text here"></textarea>
      <br>
      <input type = "submit" value = "Post" >
    </form>
    </div>
  </div>
</template>

<script>
import apiService from '../services/ApiService.js'
import VoteChoice from '../components/VoteChoice.vue'
export default {
  name: 'QuestionView',
  methods: {
    getData: function () {
      return apiService.get('questions/' + this.$route.params.id)
    },
    addAnswer() {
      apiService.post('questions/'+this.$route.params.id+'/answers', {text: this.text}).then(res => this.answers = [...this.answers, res])
      this.text = ''
    },
    postComment: function (answer){
            apiService.post('answers/'+answer.id+'/comments', {text: this.text}).then(res=>[...answer.comments, res])
            this.text = ''
            //Refreshes page, couldn't find better solution right now, how to equal upper answer to this answer.
            this.$router.go()
        }
  },
  async created() {
    this.question = await this.getData();
    this.answers = this.question.answers
  },  
  data () {
    return {
      question: {},
      answers: {},
      text: ''
    }
  },
  components: {
    VoteChoice,
  }
}
</script>

<style scoped>
.questionTitle {
  text-shadow: 1px 1px 1px #ccc;
  font-size: 40px;
  width: 50%;
  height: 5%;
  margin-left: 6%;
  background: white;
  filter: drop-shadow(6px 11px 6px grey);
}
.questionBox{
  margin-top: 3%;
  margin-left:8%;
  /*width: 40%;
  margin-left: 6%;*/
  width: 80%;
  height: auto;
  background: white;
  filter: drop-shadow(5px 11px 5px grey);
  border: 1px solid lightgrey;
  display:inline-block;
}
.bestAnswer
{
  /*width: 781px; */
  display:inline-block;
  width: 40%;
  height: auto;
  background: white;
  border: 3px solid lightgrey;
  filter: drop-shadow(5px 11px 5px grey);
}
.answerBox
{
  
  margin-top: 1%;
  /*left: 57px;
  top: 765px;*/
  margin-left: 8%;
  width: 80%;
  height: auto;
  background: white;
  box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 2px 0px;
  padding-bottom: 18px;
}
.comment{
  box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 2px 0px;
  margin-top: 8px;
  margin-left:6%;
  margin-right: 7%;
  background: rgb(235, 235, 235);;
  vertical-align: middle;
  text-align: left;
}
#title{
  font-size: 20px;
  margin-left: 2%;
}
.answerBox >>> p, .questionBox >>> p
{
  display: inline-flex;
  margin-right: 3%;
  width: 85%;
}
.post_section{
  margin-top: 3%;
  margin-left:8%;
}
.sorting{
    margin-top:2%;
    margin-left:8%;
}
.commentButton
{
    margin-top: 2.5%;
    display: flex;
    justify-content: flex-end;
    margin-right: 7%
   
}
.commentButton button{
    background: rgb(250, 129, 0);
    width: 165px;
    height: 41px;
    mix-blend-mode: normal;
    color: rgb(255, 255, 255);
    font-size: 14px;
    text-align: center;
    font-weight: bold;
    font-style: normal;
    border-radius: 0px;
    border-width: 0px;
    border-style: solid;
}
</style>
