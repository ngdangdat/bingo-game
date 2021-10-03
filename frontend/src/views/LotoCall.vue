<template>
  <div>
    <div class="call-info">
      <span class="desc">Last called number: </span>
      <span
        class="number"
        v-if="rolls.length && seq > -1 && seq < rolls.length"
      >
        {{ rolls[seq] }}
      </span>
      <span class="number" v-else>not yet</span>
    </div>
    <div class="buttons">
      <button :disabled="isLoading" @click="rollForward()" class="cta">
        Call
      </button>
      <button :disabled="isLoading" @click="rollBackward()" class="cta">
        Go back
      </button>
      <button @click="copyUrl()" class="cta">
        Share
      </button>
      <button @click="reroll()" class="cta">
        Reroll
      </button>
    </div>
    <loto-call-table
      v-if="rolls.length"
      :rolls="rolls"
      :sequence="seq"
    />
  </div>
</template>

<script>
import LotoCallTable from '@/components/LotoCallTable/index.vue';

export default {
  props: [
    'id',
  ],
  components: {
    LotoCallTable,
  },
  data() {
    return {
      rolls: [],
      seq: -1,
      isLoading: false,
    };
  },
  methods: {
    rollForward() {
      if (this.seq < (this.rolls.length - 1)) {
        this.seq += 1;
      }
    },
    rollBackward() {
      if (this.seq > -1) {
        this.seq -= 1;
      }
    },
    copyUrl() {
      const d = document.createElement('input');

      document.body.appendChild(d);
      d.value = `${window.location.origin}/bingo/${this.id}/sheet`;
      d.select();
      document.execCommand('copy');
      document.body.removeChild(d);
      alert('Copied to clipboard!');
    },
    updateRoll(rollsStr) {
      this.rolls = rollsStr.split(',').map((e) => Number(e));
    },
    reroll() {
      if (window.confirm('"Are you sure about rerolling the game?"')) {
        this.$apiCaller
          .post(`bingo/${this.id}/reroll`)
          .then(() => {
            window.location.reload();
          });
      }
    },
  },
  created() {
    this.$apiCaller
      .get(`bingo/${this.id}`)
      .then((response) => {
        const { currSeq } = response.data;
        this.seq = currSeq;
      });
  },
  mounted() {
    this.$apiCaller
      .get(`bingo/${this.id}/roll`)
      .then((response) => {
        const { rollsStr } = response.data;
        this.updateRoll(rollsStr);
      });
  },
  watch: {
    seq(val) {
      this.isLoading = true;
      const $this = this;
      this.$apiCaller
        .put(`bingo/${this.id}`, {
          currSeq: val,
        })
        .then(() => {
          $this.isLoading = false;
        });
    },
  },
};

</script>

<style lang="scss">
.call-info {
  margin-bottom: 1em;
  font-size: 1.5em;
  span.number {
    font-weight: 700;
  }
}
</style>
