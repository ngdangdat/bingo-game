<template>
  <div v-if="sheets.length" class="sheets">
    <div class="sheet" v-for="sheet in sheets" :key="sheet.id">
      <a target="_blank" :href="`/bingo/${id}/sheet/${sheet.sheetId}`">{{ sheet.sheetId }}</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LotoSheets',
  props: ['id'],
  data() {
    return {
      sheets: [],
    };
  },
  methods: {
    getSheets() {
      this.$apiCaller
        .get(`bingo/${this.id}/sheet`)
        .then((response) => response.data)
        .then((sheets) => {
          this.sheets = sheets;
        });
    },
  },
  mounted() {
    this.getSheets();
  },
};
</script>

<style lang="scss" scoped>
.sheets {
  display: flex;
  flex-wrap: wrap;
}

.sheet {
  width: 15%;
  a {
    font-size: 1.5em;
  }
}
</style>
