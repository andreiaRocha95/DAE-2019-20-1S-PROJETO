<template>
  <b-container>
    <h4>Administrator Details:</h4>
    <p>Username: {{ administrator.userId }}</p>
    <p>Name: {{ administrator.name }}</p>
    <p>Email: {{ administrator.email }}</p>
    <nuxt-link to="/administrators">Back</nuxt-link>
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
