<template>
  <v-container>
    <v-card class="mx-auto" max-width="800" tile>
      <v-list>
        <v-list-item>
          <v-list-item-avatar>
            <v-img :src="userInfo.attributes.avatar_url"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <v-list-item link>
          <v-list-item-content>
            <v-list-item-title class="title">
              {{ userInfo.attributes.login }}
            </v-list-item-title>
            <v-list-item-subtitle>{{
              userInfo.attributes.html_url
            }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item three-line>
          <v-list-item-content >
            <v-list-item-title>Roles</v-list-item-title>
            <v-list-item-subtitle v-for="(authority, index) in userInfo.authorities" :key="index">
              {{authority.authority}}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-card>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      userInfo: "",
    };
  },
  methods: {
    getBookName(bookHttpUrl) {
      return bookHttpUrl.substring(bookHttpUrl.lastIndexOf("/") + 1);
    },
    getBookLink(book) {
      return (
        "/getUserInfo/" +
        (book.access === "public" ? "" : "private/") +
        book.bookId
      );
    },
  },
  created() {
    this.$http.get("/getUserInfo").then((data) => {
      this.userInfo = data.data;
    });
  },
};
</script>

<style>
</style>