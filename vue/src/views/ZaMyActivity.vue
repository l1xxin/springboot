<!-- 用户查看自己的课设活动 -->
<template>
	<div>
		<!-- 表格 -->
		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
			<el-table-column prop="id" label="ID" width="100" sortable></el-table-column>
			<el-table-column prop="name" label="活动名称" width=""></el-table-column>
			<el-table-column label="操作" width="600" align="center">
				<template slot-scope="scope">
					<el-button type="primary" @click="topics(scope.row)">查看课题 <i class="el-icon-info"></i>
					</el-button>
					<el-button type="primary" @click="tasks(scope.row)">查看任务 <i class="el-icon-info"></i>
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<!-- 分页 -->
		<div style="padding: 10px 0">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
				:page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
				:total="total">
			</el-pagination>
		</div>

	</div>
</template>

<script>
	export default {
		name: "ZasMyActivity",
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
				//如果是教务员 就查看所有的课设活动
				if (this.user.role == 'ROLE_EDU') {
					this.request.get("/activity/page", {
					  params: {
					    pageNum: this.pageNum,
					    pageSize: this.pageSize,
					  }
					}).then(res => {
					  this.tableData = res.data.records
					  this.total = res.data.total
					  console.log(this.tableData)
					  console.log(res.data)
					})
				} else {
					//如果是学生或老师，就根据id查询自己参与的课设活动
					this.request.get("/userActivity/findMyAct/" + this.user.username).then(res => {
						if (res.code == '200') {
							// this.$message.success("查询成功")
							this.tableData = res.data
						} else {
							this.$message.success("查询失败")
						}
					})
				}

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
				// console.log(pageSize)
				this.pageSize = pageSize
				this.load()
			},
			handleCurrentChange(pageNum) {
				// console.log(pageNum)
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
			},
			//根据活动id查看当前活动的拟题记录 tea_topic
			topics(row) {
				const data = {
					id: row.id,
					name: row.name
				};
				sessionStorage.setItem("Act", JSON.stringify(data));
				//根据角色不同进入不同的页面
				if (this.user.role == 'ROLE_TEA') {
					this.$router.push('/ztTeaTopic')
				}
				if (this.user.role == 'ROLE_STU') {
					console.log(this.user.role)
					this.$router.push('/zsChoseTopic')
				}
				if (this.user.role == 'ROLE_EDU') {
					this.$router.push('/zeCheckTopic')
				}
			},
			tasks(row){
				const data = {
					id: row.id,
					name: row.name
				};
				sessionStorage.setItem("Act", JSON.stringify(data));
				//根据角色不同进入不同的页面
				if (this.user.role == 'ROLE_TEA') {
					this.$router.push('/ztCheckTask')
				}
				if (this.user.role == 'ROLE_STU') {
					console.log(this.user.role)
					this.$router.push('/zsMyTask')
				}
				if (this.user.role == 'ROLE_EDU') {
					this.$router.push('/zeTaskManage')
				}
			}
		}
	}
</script>


<style>
	.headerBg {
		background: #eee !important;
	}
</style>
