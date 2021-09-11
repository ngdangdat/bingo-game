<template>
  <div>
    <div class="buttons">
      <button @click="resetMarking()" class="cta">
        Xoá hết quẹt lại
      </button>
    </div>
    <div class="sheet noselect" v-if="sheet.length">
      <loto-sheet-table
        :sheet="sheet"
        :init-marking="initMarking"
        @toggleMark="updateMarking"
      />
    </div>
  </div>
</template>

<script>
import LotoSheetTable from '@/components/LotoSheetTable/index.vue';

const CHUNK = 9;

export default {
  components: {
    LotoSheetTable,
  },
  props: ['id', 'sheetId'],
  name: 'LotoSheet',
  data() {
    return {
      sheet: [],
      initMarking: [],
    };
  },
  methods: {
    getMarkingKey() {
      return `${this.id}_${this.sheetId}`;
    },
    getSheet(gameId, sheetId) {
      return this.$apiCaller
        .get(`bingo/${gameId}/sheet/${sheetId}`)
        .then((response) => {
          const sheetData = [];
          const { cellsStr } = response.data;
          const parsedCells = cellsStr.split(',').map((e) => Number(e));
          for (let i = 0; i < parsedCells.length; i += CHUNK) {
            sheetData.push(parsedCells.slice(i, i + CHUNK));
          }
          return sheetData;
        });
    },
    updateMarking(marking) {
      const key = this.getMarkingKey();
      if (marking === null) {
        if (localStorage.getItem(key)) {
          localStorage.removeItem(this.getMarkingKey());
        }
      } else {
        localStorage.setItem(key, JSON.stringify(marking));
      }
    },
    resetMarking() {
      if (window.confirm('"Em có chắc không?"')) {
        this.$emit('resetMarking');
      }
    },
  },
  mounted() {
    const key = this.getMarkingKey();
    if (localStorage.getItem(key)) {
      try {
        const marking = JSON.parse(localStorage.getItem(key));
        this.initMarking = marking;
      } catch (e) {
        console.log(e);
      }
    }
    this.getSheet(this.id, this.sheetId)
      .then((sheet) => {
        // init marking
        this.sheet = sheet;
      });
  },
};
</script>

<style lang="scss" scoped>
.sheet {
  color: #ef3939;
  max-width: 15em;
  display: block;
  margin: 0 auto;
  text-align: center;
  font-size: 1.85em;

  .sheet-row {
    display: flex;
    padding: .1em .1em 0;
    justify-content: space-around;
  }

  .sheet-cell {
    background-color: #ffff9f;
    border: solid 1px #000;
    width: 1.5em;
    height: 1.35em;
    &.empty {
      background-color: #ab6134;
      color: #ab6134;
    }
    &.marked:after {
      position: absolute;
      margin-left: -30px;
      margin-top: -8px;
      content: '\2713';
      font-size: 1.5em;
      color: #2d7931;
      text-align: center;
      z-index: 9999;
      opacity: 80%;
    }
  }

  .break {
    padding-bottom: .25em;
  }
}

.buttons {
  display: block;
  .cta {
    width: 16em;
  }
}

</style>
