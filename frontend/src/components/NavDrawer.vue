<template>
  <v-navigation-drawer app v-model="drawer" :clipped="$vuetify.breakpoint.lgAndUp">

    <v-list>
      <v-list-item-group color="primary">
        <v-list-item v-for="(link, i) in links" :key="i" :to="link.route" v-show="link.isShow">
          <v-list-item-icon>
            <v-icon v-text="link.icon"></v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="link.text"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  mounted() {
    this.$root.$on("show_or_hide_nav_drawer", drawer => {
      this.drawer = drawer;
    });
    this.$root.$on("show_or_hide_admin", isShow => {
      this.links[2].isShow = isShow;
    });
  },

  data: () => ({
    drawer: false,
    links: [
      { icon: "dashboard", text: "Dashboard", route: "/" ,isShow: true},
      { icon: "grid_on", text: "Grid", route: "/grid",isShow: true },
      { icon: "perm_identity", text: "Admin", route: "/admin" ,isShow: false},
    ]
  })
};
</script>
