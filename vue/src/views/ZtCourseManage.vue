<template>
  <div>
	<div id="head">
		<h1>{{init.name}}</h1>
	</div>
	<!-- 表格 -->
	<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
	  <el-table-column prop="topicName" label="题目名称"></el-table-column>
	  <el-table-column prop="stuId" label="学号"></el-table-column>
	  <el-table-column prop="stuName" label="姓名"></el-table-column>
	
	  <el-table-column label="操作"  width="180" align="center">
	    <template slot-scope="scope">
	      <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
	      <el-popconfirm
	          class="ml-5"
	          confirm-button-text='确定'
	          cancel-button-text='我再想想'
	          icon="el-icon-info"
	          icon-color="red"
	          title="您确定删除吗？"
	          @confirm="del(scope.row.id)"
	      >
	        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
	      </el-popconfirm>
	    </template>
	  </el-table-column>
	</el-table>
	<div style="padding: 10px 0">
	  <el-pagination
	      @size-change="handleSizeChange"
	      @current-change="handleCurrentChange"
	      :current-page="pageNum"
	      :page-sizes="[2, 5, 10, 20]"
	      :page-size="pageSize"
	      layout="total, sizes, prev, pager, next, jumper"
	      :total="total">
	  </el-pagination>
	</div>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
		init: {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      const data = JSON.parse(sessionStorage.getItem("Act"));
      this.init = data;
	  this.request.get("/actTopicView/page", {
	    params: {
	      pageNum: this.pageNum,
	      pageSize: this.pageSize,
	      actId:this.init.id,
		  teaId:this.user.username,
	    }
	  }).then(res => {
	    this.tableData = res.data.records
	    this.total = res.data.total
	  })
    },
    
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      this.$nextTick(() => {
        if(this.$refs.img) {
           this.$refs.img.clearFiles();
         }
         if(this.$refs.file) {
          this.$refs.file.clearFiles();
         }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
       this.$nextTick(() => {
         if(this.$refs.img) {
           this.$refs.img.clearFiles();
         }
         if(this.$refs.file) {
          this.$refs.file.clearFiles();
         }
       })
    },
    
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleFileUploadSuccess(res) {
      this.form.file = res
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    download(url) {
      window.open(url)
    },
    
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>


<style>
	.headerBg {
		background: #eee !important;
	}

	#head {
		font-family: "STXingkai", Times, serif;
		font-size: 28px;
	}
</style>
