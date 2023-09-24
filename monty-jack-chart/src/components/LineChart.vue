<template>
    <Line :data="chartData" :options="options"></Line>
</template>

<script>
import {Line} from 'vue-chartjs'
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js'

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend)

export default {
    components: {
        // eslint-disable-next-line vue/no-reserved-component-names
        Line
    },
    props: {
        chartData: {},
        options: {}
    },
}
</script>
