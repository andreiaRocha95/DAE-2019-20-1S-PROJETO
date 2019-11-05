<template>
  <b-container class="p-4">
    <h4>Coach Details:</h4>
    <b-container>
      <br/>
      <p><b>UserId:</b> {{ coach.userId }}</p>
      <p><b>Name:</b> {{ coach.name }}</p>
      <p><b>Email:</b> {{ coach.email }}</p>
    </b-container>

    <nuxt-link to="/coaches"><b-button >Back</b-button></nuxt-link>

  </b-container>
</template>
<script>
    export default {
        data() {
            return {
                coach: {}
            }
        },
        computed: {
            userId() {
                return this.$route.params.userId
            }
        },
        created() {
            this.$axios.$get(`/api/coaches/${this.userId}`)
                .then(coach => this.coach = coach || {})
        },
    }
</script>
