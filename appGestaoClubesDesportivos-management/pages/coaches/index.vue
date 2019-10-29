<template>
  <!-- easy components usage, already shipped with bootstrap css-->
  <div>
    <b-container>
      <b-table striped over :items="coaches" :fields="fields">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-link"
            :to="`/coaches/${row.item.userId}`">Details</nuxt-link>
        </template>
      </b-table>
      <br/>
      <b-button variant="outline-primary"><nuxt-link to="/create">Create a New User</nuxt-link></b-button>
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
