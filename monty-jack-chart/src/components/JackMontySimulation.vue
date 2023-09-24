<template>
  <v-container class="fill-height">
    <v-responsive class="align-center text-center fill-height">
      <h2 class="pb-4">Jack Monty Simulation Chart</h2>
      <div class="align-center justify-center">
        <v-card width="800px" elevation="0" class="d-flex align-center">
          <v-text-field disabled v-model="iterations" class="mr-2 ml-2" density="compact" label="Iterations"></v-text-field>
          <v-text-field v-model="islands" class="mr-2 ml-2" density="compact" label="Islands"></v-text-field>
          <v-text-field v-model="exclude" class="mr-2 ml-2" density="compact" label="Excluded islands"></v-text-field>
        </v-card>
        <v-card :loading="loading" height="50vh" elevation="8" class="ma-2 pa-4 mb-4">
          <LineChart ref="chart" v-if="loaded" :chartData="chartData" :options="options"/>
        </v-card>
        <v-btn @click="runSimulation()" variant="elevated" color="green">Run simulation</v-btn>
      </div>
    </v-responsive>
  </v-container>
</template>

<script lang="ts">
//
import LineChart from "@/components/LineChart.vue";
import JackMontyAPI from '@/service/JackMontyAPI';


export default {

  name: "jack-monty-simulation",
  components: {LineChart},
  data() {
    return {
      loading: false,
      loaded: false,
      iterations: 1000,
      islands: 3,
      exclude: 1,
      data: {
        labels: [],
        datasets: [],
      },
    }
  },
  methods: {
    async runSimulation() {
      this.loaded = false;
      this.loading = true;
      let stayedList = [];
      let switchedList = [];
      let switched = 0;
      let stayed = 0;
      for (let i = 0; i < this.iterations; i++) {
        await JackMontyAPI.getJackMontyResult(this.islands, this.exclude).then((response) => {
          let data = response.data;
          if (data.stayed === true) {
            stayed++;
          } else if (data.switched === true) {
            switched++;
          }
          switchedList.push(switched / this.iterations)
          stayedList.push(stayed / this.iterations)
        })
      }
      this.data.labels.splice(0, this.data.labels.length)
      this.data.labels.push.apply(this.data.labels, [...Array(this.iterations).keys()]);
      this.data.datasets = [
        {
          label: "Stayed",
          backgroundColor: [
            '#334783',
          ],
          data: stayedList,
          tension: 0.1
        }, {
          label: "Switched",
          fill: false,
          backgroundColor: [
            '#259d12',
          ],
          data: switchedList,
          tension: 0.1
        }
      ]
      this.loaded = true;
      this.loading = false;
    }
  },
  computed: {
    chartData() {
      return this.data;
    },
    options() {
      return {
        legend: {
          display: true
        },
        responsive: true,
        maintainAspectRatio: false
      }
    },
  },

}
</script>

