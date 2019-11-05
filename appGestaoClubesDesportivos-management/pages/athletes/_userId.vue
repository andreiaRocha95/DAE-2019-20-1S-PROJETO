<template>
  <b-container class="p-4">
  <h4>Athlete Details:</h4>
  <b-container>
  <br/>
    <p><b>UserId:</b> {{ athlete.userId }}</p>
    <p><b>Name:</b> {{ athlete.name }}</p>
    <p><b>Email:</b> {{ athlete.email }}</p>
    <p><b>Club Name:</b> {{ athlete.clubName }}</p>

    <h4>Modalities enrolled:</h4>
    <b-table v-if="modalities.length" striped over :items="modalities"
             :fields="modalityFields" />
    <p v-else>No subjects enrolled.</p>
  </b-container>

      <nuxt-link to="/athletes"><b-button >Back</b-button></nuxt-link>


  </b-container>
</template>
<script>
    export default {
        data() {
            return {
                athlete: {},
                modalities: [],
                modalityFields:['modalityCode', 'name', 'clubCode']
            }
        },
        computed: {
            userId() {
                return this.$route.params.userId
            }
        },
        created() {
            this.$axios.$get(`/api/athletes/${this.userId}`)
                .then(athlete => this.athlete = athlete || {})
                .then(() => this.$axios.$get(`/api/athletes/${this.username}/modalities`))
                .then(modalities => this.modalities = modalities)
        },
    }
</script>

<style>

</style>
