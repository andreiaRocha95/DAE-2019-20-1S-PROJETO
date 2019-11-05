// file default.vue --> represents the default app layout, if not specified in a page which layout to use.
<template>
    <div>
      <div>
        <b-navbar toggleable="lg" type="dark" variant="danger">
          <b-navbar-brand>
            <img src="https://placekitten.com/g/30/30" class="d-inline-block align-top" alt="Kitten">
            <nuxt-link to="/" class="textLinkNuxt">AppGCD</nuxt-link>
          </b-navbar-brand>

          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

          <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
              <b-nav-item href="#">Link</b-nav-item>
            </b-navbar-nav>

            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">

              <b-nav-item-dropdown right>
                <!-- Using 'button-content' slot -->
                <template v-slot:button-content>
                  <em>User</em>
                </template>
                <b-dropdown-item href="#">Profile</b-dropdown-item>
                <b-dropdown-item href="#">Sign Out</b-dropdown-item>
              </b-nav-item-dropdown>
            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>
        <b-row class="secondMenu">
          <b-col cols="2" class="bg-light ">

            <b-nav vertical>

              <b-nav-text class="p-3">Management</b-nav-text>
              <b-nav-item>
                <nuxt-link to="/administrators" class="btn btn-link">Administrators</nuxt-link>
              </b-nav-item>
              <b-nav-item>
                <nuxt-link to="/coaches" class="btn btn-link">Coaches</nuxt-link>
              </b-nav-item>
              <b-nav-item>
                <nuxt-link to="/athletes" class="btn btn-link">Athletes</nuxt-link>
              </b-nav-item>
              <b-nav-item>
                <nuxt-link to="/partners" class="btn btn-link">Partners</nuxt-link>
              </b-nav-item>
            </b-nav>
          </b-col>
          <b-col cols="10">
            <nuxt /> <!-- mandatory. DO NOT REMOVE -->
          </b-col>
        </b-row>

    </div>

</template>
<style>
  a{
    color:white;
  }
  a:hover{
    text-decoration: none;
    color: white;
  }
  .secondMenu{
    height: -webkit-fill-available;
  }


</style>
