<template>
  <v-container>
    <div>
      <v-card
        class="d-flex align-content-start flex-wrap"
        color="grey lighten-5"
        flat
        tile
        min-height="400"
      >
        <v-card
          v-for="book in books"
          :key="book.bookId"
          width="250"
          height="250"
          class="pa-4 ma-3"
          outlined
          tile
        >
          <v-card-title class="pb-0">{{ book.bookId }}</v-card-title>
          <v-card-text>{{getBookName(book.httpUrl)}}</v-card-text>
          <v-btn color="orange" text>
            <a :href="getBookLink(book)">Read</a>
          </v-btn>
        </v-card>
      </v-card>
    </div>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      books: []
    };
  },
  methods: {
    getBookName(bookHttpUrl) {
      return bookHttpUrl.substring(bookHttpUrl.lastIndexOf("/") + 1);
    },
    getBookLink(book) {
      return (
        "/gitBooks/" +
        (book.access === "public" ? "" : "private/") +
        book.bookId
      );
    }
  },
  created() {
    this.$http
      .get("/builder/exportBooks")
      .then(data => {
        this.books = data.data;
        this.books.sort((b1, b2) => b1.bookId - b2.bookId);
      });
  }
};
</script>

<style>
</style>