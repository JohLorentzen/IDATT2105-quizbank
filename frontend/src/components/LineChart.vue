<template>
  <Chart
      :size="{ width: 800, height: 400 }"
      :data="chartData"
      :margin="margin"
      :direction="'horizontal'">

    <template #layers>
      <Grid strokeDasharray="2,2"/>
      <Line :dataKeys="['name', 'score']"/>
    </template>
    <template #widgets>
      <Tooltip
          borderColor="#48CAE4"
          :config="{
          name: { hide: true },
          score:{ label: 'Score',  color: 'blue' },
          attemptTime: { label: 'Time', color: 'red' },
          quizName: { label: 'Quiz Name', color: 'green' }
        }"
      />
    </template>
  </Chart>
</template>

<script lang="ts">
import {defineComponent, ref, watch} from 'vue';
import {Chart, Grid, Line, Tooltip} from 'vue3-charts';

export default defineComponent({
  name: 'LineChart',
  components: {Chart, Grid, Line, Tooltip},
  props: {
    chartData: Array
  },
  setup(props) {
    const data = ref(props.chartData);
    const margin = ref({
      left: 0,
      top: 20,
      right: 20,
      bottom: 0
    });

    watch(() => props.chartData, (newVal) => {
      data.value = newVal;
    });

    return {data, margin};
  }
});
</script>
