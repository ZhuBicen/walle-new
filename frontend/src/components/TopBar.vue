<template>
  <div>
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="blue darken-3" dark flat>
      <v-row>
        <v-col>
          <div display="d-flex flex-row">
            <v-btn icon large @click="showDrawer()">
              <v-avatar size="32px" item>
                <!-- <v-img src="walle.jpg" alt="Vuetify"></v-img> -->
              </v-avatar>
            </v-btn>
          </div>
        </v-col>
        <v-col>
          <v-text-field
            light
            solo
            hide-details
            clearable
            validate-on-blur
            prepend-inner-icon="search"
            label="Search in WALLE Book"
            class="hidden-sm-and-down"
            :messages="searchResult.toString()"
            :loading="false"
            @keyup.enter.native="keywordChange"
            v-model="keyword"
          ></v-text-field>
        </v-col>
        <v-col>
          <v-card flat color="blue darken-3" class="d-flex flex-row-reverse align-center">
            <v-btn @click="showUserInfo" v-show="isLogin" text>
              <v-icon right>account_circle</v-icon>
            </v-btn>
            <v-btn v-show="!isLogin" text :href="getLoginUrl()">
              SIGN IN
              <v-icon right>account_circle</v-icon>
            </v-btn>
            <v-btn icon to="grid">
              <v-icon>apps</v-icon>
            </v-btn>
          </v-card>
        </v-col>
      </v-row>
    </v-app-bar>

    <v-snackbar color="error" v-model="showSearchError">
      {{ searchErrorResult }}
      <v-btn color="white" text @click="showSearchError = false">Close</v-btn>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  data: () => ({
    drawer: false,
    keyword: "",
    searchResult: [],
    loading: true,
    showSearchError: false,
    searchErrorResult: "",
    isLogin: false,
    currentUser: ""
  }),
  methods: {
    getLoginUrl() {
      return "/oauth2/authorization/ldap-oauth";
    },

    showUserInfo() {
      this.$router.replace("/user");
    },
    logout() {
      this.$http.post("/logout").then(
        () => {
          (window.location.href =
            "/login.html?logout"),
            (this.isLogin = false);
        },
        () => {
          window.location.href = "/";
        }
      );
    },
    showDrawer() {
      this.drawer = !this.drawer;
      this.$root.$emit("show_or_hide_nav_drawer", this.drawer);
    },
    keywordChange() {
      this.$router.push("/search");
      this.loading = true;
      this.$http
        .get(
            "/builder/search/" +
            "?q=" +
            encodeURIComponent(this.keyword)
        )
        .then(response => {
          this.loading = false;
          var result = response.data.hits.hits;
          this.searchResult = [];
          for (var i = 0; i < result.length; i++) {
            var item = {};
            item.bookName = result[i]._source.bookName;
            item.title = result[i]._source.title;
            if (result[i]._source.access == "private") {
              item.url =
                "http://walle.china.nsn-net.net/gitBooks/private/" +
                result[i]._source.bookId +
                "/" +
                result[i]._source.url;
            } else {
              item.url =
                "http://walle.china.nsn-net.net/gitBooks/" +
                result[i]._source.bookId +
                "/" +
                result[i]._source.url;
            }
            this.searchResult.push(item);
          }
          this.loading = false;
          this.$root.$emit("show_search_result", this.searchResult, response);
        });
    },
    getUser() {
      var topbar = this;
      this.$http
        .get("/getUserName")
        .then(response => {
          if (response.bodyText !== "") {
            topbar.isLogin = true;
            topbar.currentUser = response.bodyText;
            topbar.$root.$emit(
              "show_or_hide_admin", 
                response.bodyText == "bicen.zhu@nokia-sbell.com" || response.bodyText == "shuyan.zhang@nokia-sbell.com"
            );
          } else {
            topbar.isLogin = false;
          }
        });
    }
  },
  watch: {
    keyword: function(val) {
      if (val === null || val === "") {
        this.$root.$emit("clear_search_result");
        this.$router.push("/");
        return;
      }
    }
  },
  mounted() {
    this.getUser();
  }
};
</script>

<style scoped>
.rightSide {
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
}
</style>