<template>
  <v-app id="inspire">
    <NavDrawer></NavDrawer>
    <TopBar></TopBar>
    <v-content>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import NavDrawer from "./components/NavDrawer";
import TopBar from "./components/TopBar";
export default {
  name: "App",

  components: {
    NavDrawer,
    TopBar
  }
};
</script>
