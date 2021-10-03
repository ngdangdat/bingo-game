<template>
  <div class="home">
    <button @click="createGame()" class="cta">Start</button>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
  methods: {
    createGame() {
      this.$apiCaller
        .post('bingo')
        .then((response) => {
          this.$router.push(`/bingo/${response.data.id}`);
        });
    },
  },
};
</script>
