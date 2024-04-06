<template>
  <Chart
      :size="size"
      :data="chartData"
      :margin="margin"
      :direction="'horizontal'"
      ref="chartRef">


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
import {defineComponent, ref, watch, onMounted, onUnmounted, nextTick} from 'vue';
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
    const size = ref({ width: 0, height: 400});
    const chartRef = ref(null);

    const updateSize = () => {
      nextTick(() => {
        if (chartRef.value) {
          size.value = { width: chartRef.value.$el.parentNode.offsetWidth, height: 400};
        }
      });
    };

    onMounted(() => {
      window.addEventListener('resize', updateSize);
      updateSize();
    });

    onUnmounted(() => {
      window.removeEventListener('resize', updateSize);
    });

    watch(() => props.chartData, (newVal) => {
      data.value = newVal;
    });

    return {data, margin, size, chartRef};
  }
});
</script>