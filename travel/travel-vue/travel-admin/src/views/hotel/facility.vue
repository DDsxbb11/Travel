<script lang="ls">
import {getFacilityByPage,deleteFacility,addOrUpdateFacility} from "@/api/facility";
export default {
  methods: {
    confirmEvent(id){
      return new Promise(()=>{
        deleteFacility(id).then(response=>{
          this.getAll(this.param.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    cancelForm(){
      this.param.name=""
      this.param.type=""
    },
    getAll(val) {
      this.param.pageNum = val;
      return new Promise(() => {
        getFacilityByPage(this.param).then((response) => {
          const { data } = response;
          console.log(data);
          if (!data) {
            return reject("网络异常.");
          }
          this.total = data.total;
          this.tableData = data.records;
          console.log(this.tableData);
        });
      });
    },
    
    search(){
      this.getAll(1)
    }
  },

  mounted() {
    this.getAll(1);
  },
  data() {
    return {
      total: 0,
      tableData: [],
      param: {
        pageNum: 1,
        pageSize: 7,
        name: "",
        type: "",
      },
      options: [
        { value: 'hotel_facility', label: '酒店设施'},
        { value: 'room_facility', label: '客房设施'},
        { value: 'room_bathroom_facility', label: '浴室设施' },
        { value: 'room_food_facility', label: '食品设施' }
      ],
    };
  },
};
</script>
<template>
  <div style="height: 700px; color: #000">
    <div class="list_info">
      <div style="margin-top: 15px">
        <div
          class="input-suffix"
          style="margin-left: 40px; margin-top: 20px; position: absolute"
        >
          <el-input
            size="small"
            placeholder="按主题名称查找"
            v-model="param.name"
            class="input-with-select"
          >
          </el-input>
          <el-select
            v-model="param.type"
            placeholder="请选择类型"
            size="small"
            style="width: 180px; margin-left: 20px"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>

          <el-button
            type="primary"
            size="small"
            style="margin-left: 20px; width: 80px"
            @click="search"
            >搜索</el-button
          >
          <el-button
            type="info"
            size="small"
            style="margin-left: 20px; width: 80px"
            @click="cancelForm"
            >重置</el-button
          >
        </div>
      </div>

      <div class="ppp">
        <div class="obtion">
          <router-link :to="{ path: `/hotel/addOrUpdateFacility/-1` }">
            <el-button type="primary" size="small" class="el-icon-plus">
              添加设施</el-button
            >
          </router-link>
        </div>
        <el-table :data="tableData" border style="width: 100%" max-height="490">
          <el-table-column label="编号" width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="name"
            label="名称"
            width="130"
            align="center"
          ></el-table-column>
          <el-table-column prop="tag" label="类型" width="180" align="center">
             <template slot-scope="scope">
              <el-tag v-if="scope.row.type==='hotel_facility'" style="background-color: #409eff; color: #fff">酒店设施</el-tag>
              <el-tag v-if="scope.row.type==='room_facility'" style="background-color: #67c23a; color: #fff">客房设施</el-tag>
              <el-tag v-if="scope.row.type==='room_food_facility'" style="background-color: #e6a23c; color: #fff">食品设施</el-tag>
              <el-tag v-if="scope.row.type==='room_bathroom_facility'" style="background-color: #f56c6c; color: #fff">浴室设施</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="Svg图标" width="240" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.svgUrl" style="width: 100px; height: 70px;">
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            width="200"
            align="center"
          ></el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="260"
            align="center"
          >
            <template slot-scope="scope">
              <router-link :to="{ path: `/hotel/addOrUpdateFacility/${scope.row.id}` }">
                <el-button type="primary" size="mini">修改</el-button>
              </router-link>
              <el-popconfirm
                title="确定要删除吗？"
                @onConfirm="confirmEvent(scope.row.id)"
              >
                <el-button
                  slot="reference"
                  type="danger"
                  size="mini"
                  style="margin-left: 10px"
                  >删除</el-button
                >
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            :page-size="7"
            :page-count="8"
            layout="prev, pager, next"
            @current-change="getAll"
            :total="total"
            style="position: relative; margin-left: 35%"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.list_info {
  width: 98%;
  height: 98%;
  margin-left: 1%;
  margin-top: 8px;
  border: 1px solid #dddddd;
  background-color: #fff;
  border-radius: 7px;
}

.obtion {
  width: 98%;
  margin-left: 1%;
  margin-top: 10px;
  margin-bottom: 10px;
  border-radius: 7px;
  background-color: #ffffff;
}
.ppp {
  width: 98%;
  margin-top: 100px;
  margin-left: 1%;
  border: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ffffff;
}

.el-select .el-input {
  width: 130px;
}

.input-with-select {
  width: 180px;
  margin-left: 5px;
  height: 4px;
}
</style>

