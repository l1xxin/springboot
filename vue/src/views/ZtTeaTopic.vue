<template>
	<div>
		<div id="head">
		<h1>{{init.name}}</h1>
	</div>
	<!-- 按钮 -->
	<div style="margin: 10px 0">
	  <el-button type="primary" @click="handleAdd">课题申报 <i class="el-icon-circle-plus-outline"></i></el-button>
	</div>
	<!-- 表格 -->
	<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" >
	  <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
	  <el-table-column prop="name" label="题目名称"></el-table-column>
	  <el-table-column prop="des" label="题目描述"></el-table-column>
	  <el-table-column label="文件">
		  <template slot-scope="scope">
			  <el-button type="primary" @click="download(scope.row.file)" v-if="scope.row.file">下载</el-button>
		  </template>
	  </el-table-column>
	  <el-table-column prop="nickname" label="申报人"></el-table-column>
	  <el-table-column prop="status" label="审批状态" sortable>
		  <template slot-scope="scope">
		  	<el-tag type="warning" effect="dark" v-if="scope.row.status==2">待审批</el-tag>
		  	<el-tag type="success" effect="dark" v-if="scope.row.status==1">已通过</el-tag>
		  	<el-tag type="danger" effect="dark" v-if="scope.row.status==0">未通过</el-tag>
		  </template>
	  </el-table-column>
	  <el-table-column label="操作"  width="180" align="center">
	    <template slot-scope="scope">
	      <el-button type="success" @click="handleEdit(scope.row)">查看详情 <i class="el-icon-edit"></i></el-button>
	    </template>
	  </el-table-column>
	</el-table>
	<!-- 弹窗dialog -->
	<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
	  <el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
	    <!-- <el-form-item label="申报人id">
	      <el-input v-model="form.teaId" autocomplete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="活动id">
	      <el-input v-model="form.actId" autocomplete="off"></el-input>
	    </el-form-item> -->
	    <el-form-item label="题目名称">
	      <el-input v-model="form.name" autocomplete="off" type="text" maxlength="15" show-word-limit></el-input>
	    </el-form-item>
	    <el-form-item label="题目描述">
	      <el-input v-model="form.des" autocomplete="off" type="textarea" maxlength="100" show-word-limit></el-input>
	    </el-form-item>
	    <el-form-item label="任务书">
	      <el-upload action="http://localhost:9090/file/upload" ref="file" :on-success="handleFileUploadSuccess">
	        <el-button size="small" type="primary">点击上传</el-button>
	      </el-upload>
	    </el-form-item>
	
	  </el-form>
	  <div slot="footer" class="dialog-footer">
	    <el-button @click="dialogFormVisible = false">取 消</el-button>
	    <el-button type="primary" @click="save">确 定</el-button>
	  </div>
	</el-dialog>
	</div>
</template>

<script>
	export default {
		// 教师拟题申报页面
		name: "ZtTeaTopic",
		data() {
			return {
				init:{},
				tableData: [],
				total: 0,
				pageNum: 1,
				pageSize: 10,
				name: "",
				form:{},
				inForm: {
					id:null,
					teaId:null,
					actId:null,
					name:null,
					des:null,
					file:null,
					status:2,
					state:null
				},
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
				//获取上一个页面传过来的活动id
				const data = JSON.parse(sessionStorage.getItem("Act"));
				this.init = data;
				this.inForm.teaId = this.user.username;
				this.inForm.actId = this.init.id;
				this.request.get("/teaTopic/findApplyByActId/" + data.id).then(res => {
					if (res.code == '200') {
						// this.$message.success("查询成功")
						this.tableData = res.data
					} else {
						this.$message.success("查询失败")
					}
				})

			},
			// 申请课题按钮 添加方法
			save() {
			  this.inForm.name = this.form.name;
			  this.inForm.des = this.form.des;
			  this.inForm.file = this.form.file;
			  console.log(this.inFrom);
			  this.request.post("/teaTopic", this.inForm).then(res => {
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
					if (this.$refs.img) {
						this.$refs.img.clearFiles();
					}
					if (this.$refs.file) {
						this.$refs.file.clearFiles();
					}
				})
			},
			handleEdit(row) {
				this.form = JSON.parse(JSON.stringify(row))
				this.dialogFormVisible = true
				this.$nextTick(() => {
					if (this.$refs.img) {
						this.$refs.img.clearFiles();
					}
					if (this.$refs.file) {
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
