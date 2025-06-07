<script lang="ls">
import {getHotelByPage,deleteHotel} from "@/api/hotel";
import { getProvinceAndCityAndDistrictCascade, getAllTheme } from "@/api/option";

export default {
  methods: {
    confirmEvent(id){
      return new Promise(()=>{
        deleteHotel(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    handleStatusChange(row) {
      console.log(row);
    },
    
    cancelForm(){
      this.param.name=""
      this.param.province=""
      this.param.city=""
      this.param.district=""
      this.param.theme=""
    },

    truncateStr(row, column, cellValue) {
      const maxLength = 12;
      if (!cellValue) return "";
      return cellValue.length > maxLength 
        ? cellValue.slice(0, maxLength) + "..." 
        : cellValue;
    },
    getAll(val) {
      console.log(val);
      console.log(this.location);
      return new Promise(() => {
        getHotelByPage(val, 7,this.param).then((response) => {
          this.pageNum=val
          const { data } = response;
          console.log(data);
          if (!data) {
            return reject("网络异常.");
          }
          this.total = data.data.total;
          this.tableData = data.data.records;
          console.log(this.tableData);
        });
      });
    },
    
    search(){
      this.getAll(1)
    }
  },
  watch: {
    location: {
    handler(newVal) {
      console.log("location变化:", newVal);
      if (Array.isArray(newVal)) { // 使用参数newVal而不是this.location
        const [province, city,district] = newVal; // 使用解构赋值
        this.param.province = province || "";
        this.param.city = city || "";
        this.param.district = district || "";
      } else {
        this.param.province = "";
        this.param.city = "";
        this.param.district = "";
      }
    },
    deep: true // 如果location是数组且会修改元素，需要深度监听
  }
  },

  mounted() {
    this.getAll(1);
    getAllTheme().then((response) => {
      const { data } = response;
      console.log(data);
      if (!data) {
        return reject("网络异常.");
      }
      this.themeList = data.data;
    });
    getProvinceAndCityAndDistrictCascade().then((response) => {
      const { data } = response;
      console.log(data);
      if (!data) {
        return reject("网络异常.");
      }
      this.locationOptions = data.data;
    });
  },
  data() {
    return {
      total: 0,
      pageNum: 1,
      tableData: [],
      locationOptions:[],
      location: [],
      param: {
        name: "",
        province: "",
        city:"",
        district:"",
        level:""
      },
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
            placeholder="按酒店名称查找"
            v-model="param.name"
            class="input-with-select"
          >
          </el-input>
          <el-cascader
            size="small"
            v-model="location"
            :options="locationOptions"
            placeholder="请选择省 / 市  / 区"
            clearable
            class="location-cascader"
            style="width: 180px; margin-left: 20px"
          ></el-cascader>

          <el-select
            v-model="param.level"
            placeholder="酒店等级"
            size="small"
            style="width: 120px; margin-left: 20px"
          >
            <el-option
              v-for="item in themeList"
              :key="item.name"
              :label="item.name"
              :value="item.name"
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
          <router-link :to="{ path: `/hotel/addOrUpdateHotel/-1` }">
            <el-button type="primary" size="small" class="el-icon-plus">
              添加酒店</el-button
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
            label="酒店名称"
            width="180"
            align="center"
          ></el-table-column>
          
          <el-table-column
            prop="province"
            label="省份"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="city"
            label="城市"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="district"
            label="地区"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column label="酒店首图" width="240" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.imgUrl" style="width: 100px; height: 70px;">
            </template>
          </el-table-column>
          <el-table-column label="详细地址" width="240" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>{{ scope.row.address }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{
                    truncateStr(scope.row, scope.column, scope.row.address)
                  }}</el-tag>
                </div>
              </el-popover>
            </template></el-table-column
          >
          <el-table-column prop="tag" label="等级" width="100" align="center">
            <template slot-scope="scope">
              <el-tag style="background-color: #67c23a; color: #fff">{{
                scope.row.level
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="roomNumber"
            label="房间数/间"
            width="100"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="phone"
            label="联系电话"
            width="180"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="上线时间"
            width="180"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="isOnline"
            label="发布状态"
            width="100"
            align="center"
          >
            <template #default="{ row }">
              <el-switch
                v-model="row.isOnline"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="handleStatusChange(row.id)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              <router-link :to="{ path: `/hotel/addOrUpdateHotel/${scope.row.id}` }">
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
                  style="margin-left: 10px;"
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

