<template>
  <div class="data-dashboard">
    <!-- 主标题 -->
    <div class="dashboard-header">
      <h2>旅游数据综合分析</h2>
    </div>
    
    <!-- 数据概览卡片 -->
    <div class="summary-cards">
      <div class="card" v-for="(item, index) in summaryData" :key="index">
        <div class="card-icon" :style="{backgroundColor: item.color}">
          <i :class="item.icon"></i>
        </div>
        <div class="card-content">
          <h3>{{ item.title }}</h3>
          <p class="value">{{ item.value }}</p>
        </div>
      </div>
    </div>
    
    <!-- 综合图表区 -->
    <div class="chart-area">
      <!-- 销量对比柱状图 -->
      <div class="chart-card">
        <div class="chart-header">
          <h3>各类产品销量对比</h3>
        </div>
        <div ref="salesChart" class="chart"></div>
      </div>
      
      <!-- 产品分布环形图 -->
      <div class="chart-card">
        <div class="chart-header">
          <h3>各类产品分布占比</h3>
        </div>
        <div ref="distChart" class="chart"></div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { getAttractionData } from '@/api/attraction';
import { getHotelData } from '@/api/hotel';
import { getStrategyData } from '@/api/strategy';

export default {
  name: 'TourismDashboard',
  data() {
    return {
      // 概览数据
      summaryData: [
        {
          title: '总销售额',
          value: '¥1,245,680',
          rate: 12.5,
          trend: 'up',
          icon: 'el-icon-s-data',
          color: '#409EFF'
        },
        {
          title: '订单总数',
          value: '8,642',
          rate: 8.3,
          trend: 'up',
          icon: 'el-icon-s-order',
          color: '#67C23A'
        },
        {
          title: '用户数',
          value: '5,321',
          rate: 3.2,
          trend: 'up',
          icon: 'el-icon-user-solid',
          color: '#E6A23C'
        }
      ],
      // 图表实例
      salesChart: null,
      // 模拟数据
      chartData: {
        week: {
          dist: [
            { value: 6540, name: '景点门票' },
            { value: 5390, name: '酒店订阅' },
            { value: 2900, name: '旅游攻略' }
          ],
        },
      }
    };
  },
  computed: {
    // 当前时间数据
    currentData() {
      return this.chartData[this.activeTime] || this.chartData.week;
    },
    
    // 排行数据
    rankingData() {
      if (this.activeType === 'all') {
        return this.currentData.ranking;
      }
      return this.currentData.ranking.filter(item => item.type === this.activeType);
    }
  },
  mounted() {
    this.initCharts();
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
    if (this.salesChart) {
      this.salesChart.dispose();
    }
    if (this.distChart) {
      this.distChart.dispose();
    }
  },
  methods: {
    // 初始化图表
    initCharts() {
      this.$nextTick(() => {
        this.salesChart = echarts.init(this.$refs.salesChart);
        this.distChart = echarts.init(this.$refs.distChart);
        this.updateCharts();
      });
    },
    
    // 切换类型
    switchType(type) {
      this.activeType = type;
    },
    
    // 更新图表
    updateCharts() {
      if (!this.salesChart || !this.distChart) return;
      
      // 销量对比图
      const salesOption = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['景点门票', '酒店订阅', '旅游攻略'],
          axisLabel: {
            interval: 0,
            rotate: 0
          }
        },
        yAxis: {
          type: 'value',
          name: '销量'
        },
        series: [
          {
            name: '故宫',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [1560, 0, 0],
            itemStyle: {
              color: '#5470C6'
            }
          },
          {
            name: '长城',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [2340, 0, 0],
            itemStyle: {
              color: '#91CC75'
            }
          },
          {
            name: '颐和园',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [1350, 0, 0],
            itemStyle: {
              color: '#FAC858'
            }
          },
          {
            name: '天坛',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [980, 0, 0],
            itemStyle: {
              color: '#EE6666'
            }
          },
          {
            name: '圆明园',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [760, 0, 0],
            itemStyle: {
              color: '#73C0DE'
            }
          },
          {
            name: '北京饭店',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 890, 0],
            itemStyle: {
              color: '#3BA272'
            }
          },
          {
            name: '长城饭店',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 760, 0],
            itemStyle: {
              color: '#FC8452'
            }
          },
          {
            name: '香格里拉',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 1200, 0],
            itemStyle: {
              color: '#9A60B4'
            }
          },
          {
            name: '希尔顿',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 980, 0],
            itemStyle: {
              color: '#EA7CCC'
            }
          },
          {
            name: '如家酒店',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 1560, 0],
            itemStyle: {
              color: '#60C0DD'
            }
          },
          {
            name: '北京三日游',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 560],
            itemStyle: {
              color: '#008080'
            }
          },
          {
            name: '长城一日游',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 890],
            itemStyle: {
              color: '#FFA07A'
            }
          },
          {
            name: '胡同文化游',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 450],
            itemStyle: {
              color: '#20B2AA'
            }
          },
          {
            name: '美食探索',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 680],
            itemStyle: {
              color: '#778899'
            }
          },
          {
            name: '夜游北京',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: [0, 0, 320],
            itemStyle: {
              color: '#B0C4DE'
            }
          }
        ]
      };
      
      // 分布环形图
      const distOption = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center',
          data: ['景点门票', '酒店订阅', '旅游攻略']
        },
        series: [
          {
            name: '产品分布',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '18',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.currentData.dist,
            color: ['#409EFF', '#67C23A', '#E6A23C']
          }
        ]
      };
      
      this.salesChart.setOption(salesOption);
      this.distChart.setOption(distOption);
    },
    
    // 获取排名颜色
    getRankColor(index) {
      const colors = ['#FF6B6B', '#45B7D1', '#FFA502', '#A4B0BE', '#A4B0BE'];
      return colors[index] || '#A4B0BE';
    },
    
    // 窗口大小变化时重绘图表
    handleResize() {
      if (this.salesChart) {
        this.salesChart.resize();
      }
      if (this.distChart) {
        this.distChart.resize();
      }
    }
  }
};
</script>

<style scoped>
.data-dashboard {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  font-family: 'Helvetica Neue', Arial, sans-serif;
  background-color: #f5f7fa;
}

.dashboard-header {
  text-align: center;
  margin-bottom: 30px;
}

.dashboard-header h2 {
  color: #333;
  font-size: 28px;
  margin: 0 0 10px 0;
  font-weight: 500;
}

.subtitle {
  color: #909399;
  margin: 0;
  font-size: 14px;
}

/* 数据概览卡片样式 */
.summary-cards {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
  flex-wrap: wrap;
}

.card {
  flex: 1;
  min-width: 220px;
  margin: 0 10px 20px;
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  display: flex;
  transition: all 0.3s;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px 0 rgba(0, 0, 0, 0.1);
}

.card-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  color: white;
  font-size: 20px;
}

.card-content {
  flex: 1;
}

.card-content h3 {
  margin: 0 0 10px 0;
  font-size: 16px;
  color: #606266;
  font-weight: normal;
}

.card-content .value {
  margin: 0 0 5px 0;
  font-size: 24px;
  color: #303133;
  font-weight: bold;
}

.card-content .compare {
  margin: 0;
  font-size: 12px;
  color: #909399;
}

.card-content .compare span {
  margin-right: 5px;
}

.card-content .up {
  color: #67C23A;
}

.card-content .down {
  color: #F56C6C;
}

/* 图表区域样式 */
.chart-area {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.chart-card {
  flex: 1;
  min-width: 48%;
  margin-bottom: 20px;
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.chart-card.ranking {
  min-width: 100%;
}

.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.chart-header h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
  font-weight: 500;
}

.time-filter, .type-filter {
  display: flex;
  background: #f5f7fa;
  border-radius: 4px;
  padding: 4px;
}

.time-filter span, .type-filter span {
  padding: 5px 12px;
  font-size: 12px;
  cursor: pointer;
  border-radius: 3px;
  color: #606266;
}

.time-filter span.active, .type-filter span.active {
  background: #fff;
  color: #409EFF;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
}

.chart {
  width: 100%;
  height: 300px;
}

/* 排行榜样式 */
.ranking-list {
  padding: 0 10px;
}

.ranking-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.rank {
  width: 24px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  background: #f5f7fa;
  border-radius: 50%;
  margin-right: 15px;
  font-size: 12px;
  color: #909399;
}

.rank.top3 {
  background: #409EFF;
  color: white;
  font-weight: bold;
}

.name {
  width: 150px;
  font-size: 14px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.progress {
  flex: 1;
  height: 8px;
  background: #f5f7fa;
  border-radius: 4px;
  margin: 0 15px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  border-radius: 4px;
  transition: all 0.3s;
}

.value {
  width: 60px;
  text-align: right;
  font-size: 14px;
  color: #606266;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .chart-card {
    min-width: 100%;
  }
}

@media (max-width: 768px) {
  .summary-cards {
    flex-direction: column;
  }
  
  .card {
    margin: 0 0 15px 0;
  }
}
</style>