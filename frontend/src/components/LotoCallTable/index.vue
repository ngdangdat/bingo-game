<template>
  <div>
    <div class="sheet noselect">
      <div class="sheet-row" v-for="row in sheet" :key="row.toString()">
        <div
          v-for="(cell, cIndex) in row"
          :key="`${cIndex}-${marking[cell]}`"
          :class="{
            'sheet-cell': true,
            'empty': (cell == -1),
            'marked': marking[cell],
          }"
        >
          <span>
            {{ cell }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const N = 90;
let nums = [...Array(N + 1)].map(Number.call, Number);
nums = nums.slice(1, N + 1);

const numInRows = [];
const chunk = 10;
for (let i = 0; i < nums.length; i += chunk) {
  numInRows.push(nums.slice(i, i + chunk));
}

export default {
  props: [
    'rolls',
    'sequence',
  ],
  data() {
    return {
      numCol: 9,
      sheet: numInRows,
      marking: {},
    };
  },
  beforeMount() {
    this.rolls.forEach((e, index) => {
      if (index <= this.sequence) {
        this.marking[e] = true;
      } else {
        this.marking[e] = false;
      }
    });
  },
  watch: {
    sequence(nVal, oVal) {
      let changed = false;
      if (nVal === -1) {
        this.rolls.forEach((e) => {
          this.marking[e] = false;
        });
        changed = true;
      } else if (nVal > oVal) {
        this.marking[this.rolls[nVal]] = true;
        changed = true;
      } else if (nVal < oVal) {
        this.marking[this.rolls[oVal]] = false;
        changed = true;
      }
      if (changed) {
        this.$forceUpdate();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
  .sheet {
    color: #ef3939;
    // max-width: 15em;
    display: block;
    margin: 0 auto;
    text-align: center;
    font-size: 1.85em;
    font-weight: 400;

    .sheet-row {
      display: flex;
      justify-content: space-around;
      &:last-child {
        .sheet-cell {
          border-bottom: solid 1px #d80505;
        }
      }
    }

    .sheet-cell {
      flex: 1;
      background-color: #ffff9f;
      border-left: solid 1px #d80505;
      border-top: solid 1px #d80505;
      width: 1.5em;
      height: 1.35em;
      &:last-child {
        border-right: solid 1px #d80505;
      }
      &.marked {
        color: #ffff9f;
        background-color: #ef3939;
        font-weight: 700;
      }
    }
  }
</style>
