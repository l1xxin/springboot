<template>
	<div>
		<div id="head">
			<h1>{{init.name}}</h1>
		</div>
		<!-- 表格 -->
		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
			<el-table-column prop="taskName" label="任务名称" sortable></el-table-column>
			<el-table-column label="文件"><template slot-scope="scope">
					<el-button type="primary" @click="download(scope.row.file)">下载</el-button>
				</template></el-table-column>
			<el-table-column prop="des" label="任务描述"></el-table-column>
			<el-table-column prop="status" label="审核状态">
				<template slot-scope="scope">
					<el-tag type="warning" effect="dark" v-if="scope.row.status==3">待提交</el-tag>
					<el-tag type="primary" effect="dark" v-if="scope.row.status==2">待审核</el-tag>
					<el-tag type="success" effect="dark" v-if="scope.row.status==1">已通过</el-tag>
					<el-tag type="danger" effect="dark" v-if="scope.row.status==0">未通过</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="topicName" label="题目名称"></el-table-column>
			<el-table-column prop="stuId" label="学号" sortable></el-table-column>
			<el-table-column prop="stuName" label="姓名"></el-table-column>

			<el-table-column label="操作" width="180" align="center">
				<template slot-scope="scope">
					<el-button type="primary" @click="check(scope.row)" v-if="scope.row.status==2">审核<i
							class="el-icon-thumb"></i></el-button>
					<el-button type="primary" @click="detail()" v-if="scope.row.status==1||scope.row.status==0">查看详情<i
							class="el-icon-thumb"></i></el-button>
				</template>
			</el-table-column>
		</el-table>
		<div style="padding: 10px 0">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
				:page-sizes="[2, 5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
				:total="total">
			</el-pagination>
		</div>

		<!-- 弹窗dialog -->
		<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
			<el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
				<el-form-item label="题目名称">
					<el-input v-model="form.topicName" autocomplete="off" disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="任务名称">
					<el-input v-model="form.taskName" autocomplete="off" disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="任务描述">
					<el-input v-model="form.des" autocomplete="off" disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="学号">
					<el-input v-model="form.stuId" autocomplete="off" disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="姓名">
					<el-input v-model="form.stuName" autocomplete="off" disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="文件">
					<template slot-scope="scope">
						<el-button type="primary" @click="download(form.file)" v-if="form.file">下载</el-button>
					</template>
				</el-form-item>
				<el-form-item label="指导意见">
					<el-input v-model="form.comment" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
					icon-color="red" title="您确定打回吗？" @confirm="refuse()">
					<el-button type="danger" slot="reference">拒绝 <i class="el-icon-remove-outline"></i></el-button>
				</el-popconfirm>
				<el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
					icon-color="red" title="您确定通过吗？" @confirm="save()">
					<el-button type="success" slot="reference">通过 <i class="el-icon-circle-check"></i></el-button>
				</el-popconfirm>
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
				stuTask: {
					id: null,
					stuId: null,
					taskId: null,
					file: null,
					status: null,
					comment: null,
					state: 0
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
				this.request.get("/taskView/taskPage", {
					params: {
						pageNum: this.pageNum,
						pageSize: this.pageSize,
						actId: this.init.id,
						teaId: this.user.username
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
			},
			//审核任务
			check(row) {
				this.form = row
				this.dialogFormVisible = true
			},
			//通过任务
			save() {
				this.stuTask.id = this.form.id
				this.stuTask.stuId = this.form.username
				this.stuTask.taskId = this.form.taskId
				this.stuTask.file = this.form.file
				this.stuTask.status = 1
				this.stuTask.comment = this.form.comment
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
			//打回任务
			refuse() {
				this.stuTask.id = this.form.id
				this.stuTask.stuId = this.form.username
				this.stuTask.taskId = this.form.taskId
				this.stuTask.file = this.form.file
				this.stuTask.status = 0
				this.stuTask.comment = this.form.comment
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
