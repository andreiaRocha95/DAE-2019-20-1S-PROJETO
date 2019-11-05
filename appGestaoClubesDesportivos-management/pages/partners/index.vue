<template>
  <!-- easy components usage, already shipped with bootstrap css-->
  <div>
    <b-container>
      <h1> Coaches Management</h1>
      <div>
        <b-navbar toggleable="lg" type="light">
          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
          <b-collapse id="nav-collapse" is-nav>
            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
              <b-nav-item-dropdown text="Filter" right>
                <b-dropdown-item href="#">EN</b-dropdown-item>
                <b-dropdown-item href="#">ES</b-dropdown-item>
                <b-dropdown-item href="#">RU</b-dropdown-item>
                <b-dropdown-item href="#">FA</b-dropdown-item>
              </b-nav-item-dropdown>

              <b-nav-form>
                <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
                <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
              </b-nav-form>

            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>
      <b-table striped over :items="coaches" :fields="fields">
        <template v-slot:cell(actions)="row">

            <nuxt-link class="" :to="`/coaches/${row.item.userId}`">
              <b-button variant="">
              Details
              </b-button>
            </nuxt-link>


            <nuxt-link class="":to="`/coaches/${row.item.userId}`">
              <b-button variant="info">
                Edit
              </b-button>
            </nuxt-link>



            <nuxt-link class="":to="`/coaches/${row.item.userId}`">
              <b-button variant="danger">
                Delete
              </b-button>
            </nuxt-link>

        </template>
      </b-table>

      <br/>
      <nuxt-link to="/">Back</nuxt-link>

    </b-container>
    <br/>
  </div>
</template>
<script>
    export default {
        data () {
            return {
                fields: ['userId', 'name', 'email', 'actions'],
                coaches: []
            }
        },
        created () {
            this.$axios.$get("http://localhost:8080/AppGestaoClubesDesportivos_war_exploded/api/coaches/")
            this.$axios.$get("/api/coaches")
                .then(coaches => {
                    this.coaches = coaches
                })
        }
    }
</script>
<style>
</style>
