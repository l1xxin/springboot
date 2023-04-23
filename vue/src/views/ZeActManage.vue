<template>
	<div>
		<!-- 新增按钮 -->
		<div style="margin: 10px 0">
			<el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
		</div>
		<!-- 表格 -->
		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
			@selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
			<el-table-column prop="name" label="活动名称"></el-table-column>

			<el-table-column label="操作" width="600" align="center">
				<template slot-scope="scope">
					<el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
					<el-button type="primary" @click="addStu(scope.row.id)">添加学生 <i class="el-icon-edit"></i>
					</el-button>
					<el-button type="primary" @click="addTea(scope.row.id)">添加教师 <i class="el-icon-edit"></i>
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


		<!-- 新增弹窗 -->
		<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
			<el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
				<el-form-item label="活动名称">
					<el-input v-model="form.name" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="save">确 定</el-button>
			</div>
		</el-dialog>

		<!-- 学生弹窗 -->
		<el-dialog title="未加入活动学生列表" :visible.sync="dialogStuVisible" width="40%" :close-on-click-modal="false">
			<!-- 学生表格 -->
			<el-table :data="StuData" border stripe :header-cell-class-name="'headerBg'"
				@selection-change="handleStuSelection">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column prop="num" label="学号" width="80" sortable></el-table-column>
				<el-table-column prop="name" label="姓名"></el-table-column>
			</el-table>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogStuVisible = false">取 消</el-button>
				<el-button type="primary" @click="saveStuAct">添加</el-button>
			</div>
		</el-dialog>
		<!-- 教师弹窗 -->
		<el-dialog title="未加入活动教师列表" :visible.sync="dialogTeaVisible" width="40%" :close-on-click-modal="false">
			<!-- 教师表格 -->
			<el-table :data="TeaData" border stripe :header-cell-class-name="'headerBg'"
				@selection-change="handleTeaSelection">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column prop="num" label="工号" width="80" sortable></el-table-column>
				<el-table-column prop="name" label="姓名"></el-table-column>
			</el-table>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogTeaVisible = false">取 消</el-button>
				<el-button type="primary" @click="saveTeaAct">添加</el-button>
			</div>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		name: "ZeActManage",
		data() {
			return {
				act: null,
				tableData: [],
				//查询出来的未参加活动的学生列表
				StuData: [],
				TeaData: [],
				total: 0,
				pageNum: 1,
				pageSize: 10,
				name: "",
				form: {},
				dialogFormVisible: false,
				dialogStuVisible: false,
				dialogTeaVisible: false,
				multipleSelection: [],
				// 学生多选列表
				stuSelection: [],
				//教师多选列表
				teaSelection: [],
				user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
			}
		},
		created() {
			this.load()
		},
		methods: {
			load() {
				this.request.get("/activity/page", {
					params: {
						pageNum: this.pageNum,
						pageSize: this.pageSize,
						name: this.name,
					}
				}).then(res => {
					this.tableData = res.data.records
					this.total = res.data.total
				})

			},
			//保存或新增活动
			save() {
				this.request.post("/activity", this.form).then(res => {
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

			// 添加学生列表多选改变
			handleStuSelection(val) {
				console.log(val)
				this.stuSelection = val.map(({
					name,
					num
				}) => {
					return {
						userId: num,
						actId: this.act
					}
				});
				console.log(this.stuSelection);
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
			},
			// 未参加活动学生弹窗
			addStu(actId) {
				this.dialogStuVisible = true
				this.request.get("/userActivity/findStu/" + actId).then(res => {
					if (res.code == '200') {
						this.act = actId
						this.$message.success("查询成功")
						this.StuData = res.data
					} else {
						this.$message.success("查询失败")
					}
				})
			},
			// 添加学生到用户活动表
			saveStuAct() {
				if (!this.stuSelection.length) {
				  this.$message.error("请选择需要添加的用户")
				  return
				}
				this.request.post("/userActivity/addUserAct", this.stuSelection).then(res => {
					if (res.code === '200') {
						this.$message.success("添加成功")
						this.dialogStuVisible = false
						this.act = null
						this.load()
					} else {
						this.$message.error("保存失败")
					}
				})
				this.act = null
			},

			//未参加活动教师弹窗
			addTea(actId) {
				this.dialogTeaVisible = true
				this.request.get("/userActivity/findTea/" + actId).then(res => {
					if (res.code == '200') {
						this.act = actId
						this.$message.success("查询成功")
						this.TeaData = res.data
					} else {
						this.$message.success("查询失败")
					}
				})
			},
			saveTeaAct() {
				if (!this.teaSelection.length) {
				  this.$message.error("请选择需要添加的用户")
				  return
				}
				this.request.post("/userActivity/addUserAct", this.teaSelection).then(res => {
					if (res.code === '200') {
						this.$message.success("添加成功")
						this.dialogTeaVisible = false
						this.act = null
						this.load()
					} else {
						this.$message.error("保存失败")
					}
				})
				this.act = null
			},

		}
	}
</script>


<style>
	.headerBg {
		background: #eee !important;
	}
</style>
