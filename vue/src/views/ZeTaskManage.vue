<template>
	<div>
		<div id="head">
			<h1>{{init.name}}</h1>
		</div>
		<div style="margin: 10px 0">
		  <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
		</div>
		<!-- 表格 -->
		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
			<el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
			<el-table-column prop="name" label="任务名称"></el-table-column>
			<el-table-column prop="des" label="任务描述"></el-table-column>
			<!-- <el-table-column prop="deadline" label="截止时间"></el-table-column> -->
			<!-- <el-table-column prop="state" label=""></el-table-column> -->
			<el-table-column label="操作" width="180" align="center">
				<template slot-scope="scope">
					<el-button type="success" @click="assign(scope.row)" v-if="scope.row.state==0">分配任务 <i class="el-icon-edit"></i></el-button>
					<el-button type="success" @click="handleEdit(scope.row)" v-if="scope.row.state==1">查看详情 <i class="el-icon-edit"></i></el-button>
				</template>
			</el-table-column>
		</el-table>
		<div style="padding: 10px 0">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
				:page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
				:total="total">
			</el-pagination>
		</div>
		<!-- 弹窗 -->
		<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
		  <el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
		    <el-form-item label="任务名称">
		      <el-input v-model="form.name" autocomplete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="任务描述">
		      <el-input v-model="form.des" autocomplete="off"></el-input>
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
		name: "",
		data() {
			return {
				//init：id、name 活动id、活动名称
				init: {},
				tableData: [],
				total: 0,
				pageNum: 1,
				pageSize: 10,
				name: "",
				form: {
					id:null,
					actId:null,
					name:null,
					des:null,
					deadline:null,
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
				const data = JSON.parse(sessionStorage.getItem("Act"));
				this.init = data;
				this.request.get("/task/page", {
				  params: {
				    pageNum: this.pageNum,
				    pageSize: this.pageSize,
				    actId: this.init.id,
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
					if (this.$refs.img) {
						this.$refs.img.clearFiles();
					}
					if (this.$refs.file) {
						this.$refs.file.clearFiles();
					}
				})
			},
			save() {
			  this.form.actId = this.init.id
			  this.request.post("/task", this.form).then(res => {
			    if (res.code === '200') {
			      this.$message.success("保存成功")
			      this.dialogFormVisible = false
			      this.load()
			    } else {
			      this.$message.error("保存失败")
			    }
			  })
			},
			//分配任务
			assign(row){
				
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
				this.name = "ZeTaskManage"
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
