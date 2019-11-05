<template>
  <b-container class="p-4">
    <h4>Partner Details:</h4>
    <b-container>
      <br/>
      <p><b>UserId:</b> {{ partner.userId }}</p>
      <p><b>Name:</b> {{ partner.name }}</p>
      <p><b>Email:</b> {{ partner.email }}</p>
    </b-container>

    <nuxt-link to="/partners"><b-button >Back</b-button></nuxt-link>

  </b-container>
</template>
<script>
    export default {
        data() {
            return {
                partner: {}
            }
        },
        computed: {
            userId() {
                return this.$route.params.userId
            }
        },
        created() {
            this.$axios.$get(`/api/partners/${this.userId}`)
                .then(partner => this.partner = partner || {})
        },
    }
</script>
