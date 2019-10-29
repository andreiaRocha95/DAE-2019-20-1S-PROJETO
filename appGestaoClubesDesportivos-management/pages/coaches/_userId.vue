<template>
  <b-container>
    <h4>Administrator Details:</h4>
    <p>Username: {{ coach.userId }}</p>
    <p>Name: {{ coach.name }}</p>
    <p>Email: {{ coach.email }}</p>
    <nuxt-link to="/coaches">Back</nuxt-link>
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
