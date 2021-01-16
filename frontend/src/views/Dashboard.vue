<template>
  <v-container>
    <v-row>
      <v-col
        class="topic"
        :id="`topic-${topicIndex}`"
        v-for="(topic, topicIndex) in topics"
        :key="topic.topic"
        cols="12"
        md="3"
        sm="6"
        xs="12"
      >
        <h2 class="py-3 mb-3 pl-3 headline">{{topic.topic}}</h2>
        <div v-for="section in topic.sections" :key="section.name">
          <h3 class="pl-3 title">{{section.name}}</h3>
          <ul v-for="book in section.books" :key="book.title">
            <li class="body-1">
              <a :href="book.url">{{book.title}}</a>
            </li>
          </ul>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    newArticles: [],
    topics: []
  }),
  methods: {
    async getRecentUpdates() {
    //   this.$http.get("/builder/getRecentUpdate")
    //     .then(data => {
    //       this.newArticles = data.data;
    //     });
    }
  },
  async created() {
    // await this.getRecentUpdates();
    // this.$http.get("/gitBooks/config/books.json")
    //   .then(response => {
    //     this.topics = response.data;
    //   });
  }
};
</script>

<style>
@import "../assets/css/styles.css";

h2 {
  color: white;
}

h3 {
  color: black;
}
/* h2 */
#topic-0 h2 {
  background-color: #878ecd;
}
#topic-1 h2 {
  background-color: #0dceda;
}
#topic-2 h2 {
  background-color: #769fcd;
}
#topic-3 h2 {
  background-color: #fb929e;
}

/* h3 */
#topic-0 h3 {
  background-color: #dde7f2;
}
#topic-1 h3 {
  background-color: #c6fce5;
}
#topic-2 h3 {
  background-color: #d6e6f2;
}
#topic-3 h3 {
  background-color: #ffdfdf;
}
</style>