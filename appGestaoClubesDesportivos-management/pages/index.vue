<template>
  <!-- easy components usage, already shipped with bootstrap css-->
  <b-container>
    <!-- try to remove :fields=”fields” to see the magic -->
    <b-table striped over :items="administradores" :fields="fields" />
  </b-container>
</template>
<script>
    export default {
        data () {
            return {
                fields: ['userId', 'nome', 'password', 'email'],
                administradores: []
            }
        },
        created () {
            this.$axios.$get("http://localhost:8080/AppGestaoClubesDesportivos_war_exploded/api/administradores/")
            this.$axios.$get("/api/administradores")
                .then(administradores => {
                    this.administradores = administradores
                })
        }
    }
</script>
<style>
</style>
