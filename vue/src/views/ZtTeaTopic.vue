<template>
	<div>
		<!-- <div id="head">
		<h1>{{user.role}}</h1>
	</div> -->
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
	
	</div>
</template>

<script>
	export default {
		// 教师拟题申报页面
		name: "ZtTeaTopic",
		data() {
			return {
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
				//获取上一个页面传过来的活动id
				const data = JSON.parse(sessionStorage.getItem("ActId"));
				this.request.get("/teaTopic/findApplyByActId/" + data).then(res => {
					if (res.code == '200') {
						// this.$message.success("查询成功")
						this.tableData = res.data
						console.log(this.tableData)
					} else {
						this.$message.success("查询失败")
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
