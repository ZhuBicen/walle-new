<template>
  <v-row>
    <v-col></v-col>
    <v-col>
      <v-alert
        dense
        colored-border
        color="success"
        elevation="2"
        class="mb-1"
        v-for="item in searchResult"
        :key="item.url"
      ><a target="_blank" v-bind:href="item.url">[{{item.bookName}}] {{item.title}}</a></v-alert>
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      searchResult: []
    };
  },
  mounted() {
    this.$root.$on("show_search_result", searchResult => {
      this.searchResult = searchResult;
      this.$root.$emit("show_or_hide_nav_drawer", false);
    });
    this.$root.$on("clear_search_result", () => {
      this.searchResult = [];
    });
  },
  methods: {}
};
</script>