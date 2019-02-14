<template>
  <div class="hello">
    <div class = "eskast">{{title}}</div>
    <div class = "teinekast"><VoteChoice />{{question}}</div>
    <div class = "parimkast"><VoteChoice />{{bestAnswer}}</div>
    <div class = "answerkast" v-for="answer in answers"><VoteChoice />{{answer.text}}
    <div class = "comment" v-for="comment in answer.comments">
    <VoteChoice />{{comment.text}}</div>
    </div>
    <div class = "post_section">
    <form>
      <textarea v-model="message" style = "width: 50%;" placeholder="Insert text here"></textarea></br>
      <input type = "submit" value = "Post">
    </form>
    </div>
  </div>
</template>

<script>
import VoteChoice from './VoteChoice.vue'
export default {
  name: 'SingleElement',
  
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      title: 'Jesus is my savior?',
      question: "The question Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque luctus dolor libero, aliquet convallis erat cursus sit" +
      "amet. Morbi justo diam, dictum aliquet iaculis sed, tempus vitae sem. Aliquam venenatis, mauris nec ornare tempor, ligula nisi"+
      "consectetur ligula, sed lacinia magna metus et sem. Vivamus nec vulputate massa, convallis lobortis diam. Praesent eu nisi massa."+
      "Quisque eros magna, aliquet a elementum vitae, eleifend pharetra metus. Quisque fringilla porta dignissim. Morbi hendrerit pretium"+
      "nulla sed ultricies. Donec eget lobortis nisi, sed tempus velit. Quisque mauris elit, rhoncus rhoncus tempor et, malesuada sed metus."+
      "Vivamus dictum dolor ut odio gravida ullamcorper. Pellentesque tristique fermentum est ac pharetra. Morbi venenatis convallis odio,"+
      "imperdiet tristique lorem lacinia vitae. ",
      bestAnswer: "Best Answer from starts here "+
      "Donec euismod egestas mi, et pulvinar massa. Praesent cursus tellus id tortor dignissim,"+
      "vitae sagittis nunc facilisis. Cras semper ante a ante malesuada, sed fermentum neque"+
      "pretium. Nam ultrices quam facilisis odio pulvinar consequat. Donec elit mauris, tristique"+
      "sit amet ligula et, aliquam luctus erat. Etiam vitae dictum enim, rutrum laoreet neque."+
      "Vivamus ac euismod risus, tincidunt egestas ligula. Integer ut tortor in dolor porta dignissim"+
      "ut sit amet lacus. In efficitur dui leo, ut consequat orci porta sit amet. Mauris ut placerat"+
      "tellus.Cras id massa eget neque pharetra consequat sed quis nisi.",
      answers: [{id: 0, text: 'jeebus', score: 3, comments: [{id: 99, text: "esimene comment"},{id: 98, text: "teine comment"}]}, {id: 1, text: 'jeesus', score: 1}, {id: 5, text: 'viies', score: 1}, {id: 3, text: 'jeesus', score: 1}]
    
    }
  },
  components: {
      VoteChoice
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.eskast {
  width: 50%;
  height: 5%;
  margin-left: 6%;
  margin-top: 3%;
  background: #C4C4C4;
}
.teinekast{
  margin-top: 3%;
  width: 40%;
  height: auto;
  margin-left: 6%;
  background: #C4C4C4;
  display:inline-block;
}
.parimkast
{
  /*width: 781px; */
  display:inline-block;
  width: 40%;
  height: auto;
  background: rgba(113, 255, 2, 0.5);
}
.answerkast
{
  margin-top: 1%;
  /*left: 57px;
  top: 765px;*/
  margin-left: 8%;
  width: 60%;
  height: auto;
  background: red;
}
.comment{
  background: pink;
  margin-left:1%;
}
.post_section{
  margin-top: 3%;
  margin-left:6%;
}
</style>
