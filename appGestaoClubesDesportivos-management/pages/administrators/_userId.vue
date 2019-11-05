<template>
  <b-container class="p-4">
  <h4>Administrator Details:</h4>
  <b-container>
  <br/>
    <p><b>UserId:</b> {{ administrator.userId }}</p>
    <p><b>Name:</b> {{ administrator.name }}</p>
    <p><b>Email:</b> {{ administrator.email }}</p>
  </b-container>

      <nuxt-link to="/administrators"><b-button >Back</b-button></nuxt-link>


  </b-container>
</template>
<script>
    export default {
        data() {
            return {
                administrator: {}
            }
        },
        computed: {
            userId() {
                return this.$route.params.userId
            }
        },
        created() {
            this.$axios.$get(`/api/administrators/${this.userId}`)
                .then(administrator => this.administrator = administrator || {})
        },
    }
</script>

<style>

</style>
