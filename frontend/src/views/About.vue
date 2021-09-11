<template>
  <div>
    <div class="sheet noselect">
      <div class="sheet-row" v-for="(row, rIndex) in sheet" :key="row.toString()"
        :class="(rIndex != (sheet.length - 1) && (rIndex + 1) % 3 == 0) ? 'break clearfix' : ''">
        <div
          v-for="(cell, cIndex) in row"
          :key="cIndex"
          :class="[
            (cell != -1) ? '' : 'empty',
            'sheet-cell',
          ]">
          <span>
            {{ cell }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const cells = '38,78,-1,4,-1,1,53,-1,-1,-1,49,24,-1,55,-1,-1,21,27,-1,-1,85,54,-1,69,46,-1,34,8,33,63,5,-1,-1,-1,-1,20,-1,36,11,72,-1,-1,18,89,-1,42,-1,29,-1,-1,56,-1,22,90,44,81,-1,-1,-1,28,87,40,-1,16,73,39,19,-1,-1,-1,15,-1,-1,6,-1,23,76,82,-1,-1,3,17,50,-1,-1,12,-1,74,-1,37,58,-1,79,66,-1,61,-1,45,-1,57,67,47,86,-1,84,-1,-1,-1,-1,-1,-1,32,-1,43,9,62,60,25,65,-1,7,-1,10,70,-1,-1,-1,64,-1,68,14,-1,26,13,-1,51,75,-1,-1,80,48,52,-1,-1,30,-1,-1,41,83,77,-1,-1,35,-1,-1,31,-1,88,-1,59,71,2'.split(',').map((e) => Number(e));

const sheetData = [];
const chunk = 9;
for (let i = 0; i < cells.length; i += chunk) {
  sheetData.push(cells.slice(i, i + chunk));
}

export default {
  data() {
    return {
      numCol: 9,
      sheet: sheetData,
    };
  },
  mounted() {
    console.log(process.env);
  },
};
</script>

<style lang="scss">
  .sheet {
    color: #ef3939;
    max-width: 15em;
    display: block;
    margin: 0 auto;
    text-align: center;
    font-size: 1.85em;
    font-weight: 700;

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
        margin-left: -26px;
        content: "X";
        font-size: 1em;
        color: black;
        text-align: center;
        z-index: 9999;
        opacity: 10;
      }
    }

    .break {
      padding-bottom: .25em;
    }
  }
</style>
