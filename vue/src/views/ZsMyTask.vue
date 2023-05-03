<template>
  <div>
	<div id="head">
		<h1>{{init.name}}</h1>
	</div>
	<!-- 表格 -->
	<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
		<el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
		<el-table-column prop="taskName" label="任务名称" width="150"></el-table-column>
		<el-table-column label="文件" width="100">
			<template slot-scope="scope">
				<el-button type="primary" @click="handleEdit(scope.row)"  v-if="scope.row.file==null">上传文件 <i class="el-icon-edit"></i></el-button>
				<el-button type="primary" @click="download(scope.row.file)" v-if="scope.row.file">下载</el-button>
			</template>
		</el-table-column>
		<el-table-column prop="des" label="任务描述"></el-table-column>
		<el-table-column prop="status" label="审核状态" width="100">
			<template slot-scope="scope">
				<el-tag type="warning" effect="dark" v-if="scope.row.status==3">待提交</el-tag>
				<el-tag type="primary" effect="dark" v-if="scope.row.status==2">待审核</el-tag>
				<el-tag type="success" effect="dark" v-if="scope.row.status==1">已通过</el-tag>
				<el-tag type="danger" effect="dark" v-if="scope.row.status==1">未通过</el-tag>
			</template>
		</el-table-column>
		<!-- <el-table-column label="操作" width="180" align="center">
			<template slot-scope="scope">
				
			</template>
		</el-table-column> -->
	</el-table>
	<!-- 弹窗 -->
	<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
	  <el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
	    <el-form-item label="任务文件">
	      <el-upload action="http://localhost:9091/file/upload" ref="file" :on-success="handleFileUploadSuccess">
	        <el-button size="small" type="primary">点击上传</el-button>
	      </el-upload>
	    </el-form-item>
	  </el-form>
	  <div slot="footer" class="dialog-footer">
	    <el-button @click="dialogFormVisible = false">取 消</el-button>
	    <el-button type="primary" @click="save()">确 定</el-button>
	  </div>
	</el-dialog>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
		init: {},
		stuTask:{
			id:null,
			stuId:null,
			taskId:null,
			file:null,
			status:null,
			comment:null,
			state:0
		},
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
		this.request.get("/taskView/myTask", {
		  params: {
		    actId:this.init.id,
		    stuId: this.user.username
		  }
		}).then(res => {
		  this.tableData = res.data
		})
    },
    save() {
		this.stuTask.id = 
      this.request.post("/stuTask", this.stuTask).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
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
      this.dialogFormVisible = true
	  this.stuTask.id = row.id
	  this.stuTask.stuId = this.user.username
	  this.stuTask.taskId = row.taskId
	  this.stuTask.status = 2
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
      this.stuTask.file = res
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
