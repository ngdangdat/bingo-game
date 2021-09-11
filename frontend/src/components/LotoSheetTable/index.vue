<template>
  <div v-if="marking.length">
    <div class="sheet-row" v-for="(row, rIndex) in sheet" :key="row.toString()"
      :class="(rIndex != (sheet.length - 1) && (rIndex + 1) % 3 == 0) ? 'break clearfix' : ''">
      <div
        v-for="(cell, cIndex) in row"
        @click="toggleMark(rIndex, cIndex)"
        :key="cIndex"
        :class="[
          (cell != -1) ? '' : 'empty',
          'sheet-cell',
          (marking[rIndex][cIndex]) ? 'marked' : '',
        ]">
        <span>
          {{ cell }}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    sheet: Array,
    initMarking: Array,
  },
  name: 'LotoSheetTable',
  data() {
    return {
      marking: [],
    };
  },
  methods: {
    createEmptyMarking(numRow, numCol) {
      const marking = [];
      for (let r = 0; r < numRow; r += 1) {
        const arr = Array(numCol).fill(false);
        marking.push(arr);
      }
      return marking;
    },
    init() {
      if (this.initMarking && this.initMarking.length) {
        this.marking = this.initMarking;
      } else {
        const marking = this.createEmptyMarking(this.sheet.length, this.sheet[0].length);
        this.marking = marking;
      }
    },
    toggleMark(row, col) {
      if (row < this.marking.length && col < this.marking[col].length) {
        if (this.sheet[row][col] === -1) return;
        this.marking[row][col] = !this.marking[row][col];
        this.$forceUpdate();
        this.$emit('toggleMark', this.marking);
      }
    },
  },
  mounted() {
    this.init();
    this.$parent.$on('resetMarking', () => {
      const emptyMarking = this.createEmptyMarking(this.sheet.length, this.sheet[0].length);
      this.marking = emptyMarking;
      this.$emit('toggleMark', null);
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
        &.marked:after {
          content: '';
        }
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
</style>
