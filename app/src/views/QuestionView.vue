<template>
  <div class="container">
    <!--
    {{ $route.params.id }}
    <div class = "questionTitle">{{title}}</div>
    -->
    <div class = "questionBox"><p id = "title">{{question.title}}</p><br><VoteChoice  v-bind:id="question.id" v-bind:score="question.score" v-bind:canVote="true"/><p>{{question.text}}</p></div>
    <!-- Old code with best answer
    <div class = "bestAnswer" v-if="answers.length>0"><h2>Most highly rated answer:</h2><VoteChoice v-bind:id="answers[0].id" v-bind:type="post"/>{{answers[0].text}}
    <div class = "comment" v-for="comment in answers[0].comments"><VoteChoice v-bind:id="comment.id" v-bind:type="comment"/>{{comment.text}}</div></div>
    <div class = "answerBox" v-for="answer in answers" v-if="answer != answers[0]"><VoteChoice v-bind:id="answer.id" v-bind:type="post"/><p>{{answer.text}}</p>
    <div class = "comment" v-for="comment in answer.comments">
    <VoteChoice  v-bind:id="comment.id" v-bind:type="comment"/>{{comment.text}}</div>-->

    <div class = "sorting">
        Sort by:
        <select v-model="sort" placeholder>
            <option selected>Time</option>
            <option>Score</option>
        </select>
    </div>

    <!-- v-bind:canVote="true" is temporary -->
    <div class = "answerBox" v-for="answer in answers" v-bind:key="answer.id"><VoteChoice v-bind:id="answer.id" v-bind:type="post" v-bind:score="answer.score" v-bind:canVote="true"/><p>{{answer.text}}</p>
    <div class = "comment" v-for="comment in answer.comments" v-bind:key="comment.id">
    <VoteChoice  v-bind:id="comment.id" v-bind:type="comment"/>{{comment.text}}</div>
    <CommentButton v-bind:id='answer.id'/>
    </div>
    <div class = "post_section">
    <form>
      <textarea v-model="message" style = "width: 80%; height: 118px;" placeholder="Insert text here"></textarea>
      <br>
      <input type = "submit" value = "Post" v-on:click = "submitPost">
    </form>
    </div>
  </div>
</template>

<script>
import VoteChoice from '../components/VoteChoice.vue'
import CommentButton from '../components/CommentButton.vue'
export default {
  name: 'QuestionView',
  data () {
    return {
      question: {id: 0, title: "Halp me plz, I'm nub", text: "The question Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque luctus dolor libero, aliquet convallis erat cursus sit" +
      "amet. Morbi justo diam, dictum aliquet iaculis sed, tempus vitae sem. Aliquam venenatis, mauris nec ornare tempor, ligula nisi"+
      "consectetur ligula, sed lacinia magna metus et sem. Vivamus nec vulputate massa, convallis lobortis diam. Praesent eu nisi massa."+
      "Quisque eros magna, aliquet a elementum vitae, eleifend pharetra metus. Quisque fringilla porta dignissim. Morbi hendrerit pretium"+
      "nulla sed ultricies. Donec eget lobortis nisi, sed tempus velit. Quisque mauris elit, rhoncus rhoncus tempor et, malesuada sed metus."+
      "Vivamus dictum dolor ut odio gravida ullamcorper. Pellentesque tristique fermentum est ac pharetra. Morbi venenatis convallis odio,"+
      "imperdiet tristique lorem lacinia vitae.", score: 5},
      title: 'Jesus is my savior?',
      //not relevant right now.
      bestAnswer: "Best Answer from starts here "+
      "Donec euismod egestas mi, et pulvinar massa. Praesent cursus tellus id tortor dignissim,"+
      "vitae sagittis nunc facilisis. Cras semper ante a ante malesuada, sed fermentum neque"+
      "pretium. Nam ultrices quam facilisis odio pulvinar consequat. Donec elit mauris, tristique"+
      "sit amet ligula et, aliquam luctus erat. Etiam vitae dictum enim, rutrum laoreet neque."+
      "Vivamus ac euismod risus, tincidunt egestas ligula. Integer ut tortor in dolor porta dignissim"+
      "ut sit amet lacus. In efficitur dui leo, ut consequat orci porta sit amet. Mauris ut placerat"+
      "tellus.Cras id massa eget neque pharetra consequat sed quis nisi.",
      //In future assign answers = question.answers
      answers: [{id: 0, text: 'jeebus', score: -3, comments: [{id: 99, text: "esimene comment"},{id: 98, text: "teine comment"}]}, {id: 1, text: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', score: 1, comments: [{id: 99, text: "esimene comment"},{id: 98, text: "teine comment"}]}, {id: 5, text: 'viies', score: 1}, {id: 3, text: 'jeesus', score: 1}]
    }
  },
  components: {
    VoteChoice,
    CommentButton
  },
  methods: {
    submitPost: function () {
      alert()
    }
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
.answerBox p, .questionBox p
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

</style>
