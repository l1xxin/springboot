<template>
	<div>
		<div id="head">
			<h1>{{init.name}}</h1>
		</div>
		<div style="margin: 10px 0">
			<el-button type="primary" @click="choseTopic()" v-if="this.state==0">选题</el-button>
		</div>
		<!-- 表格 -->
		<el-table ref="table" :data="tableData" border stripe :header-cell-class-name="'headerBg'"
			@select-all="selectAll" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55" />
			<el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
			<el-table-column prop="name" label="题目名称" width="180"></el-table-column>
			<el-table-column prop="des" label="题目描述"></el-table-column>
			<el-table-column label="文件" width="80">
				<template slot-scope="scope">
					<el-button type="primary" @click="download(scope.row.file)" v-if="scope.row.file">下载</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="nickname" label="指导老师" width="100"></el-table-column>
			<el-table-column label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button type="success" @click="">查看详情 <i class="el-icon-info"></i></el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	export default {
		name: "",
		data() {
			return {
				state:null,
				init: {},
				tableData: [],
				total: 0,
				pageNum: 1,
				pageSize: 10,
				name: "",
				form: {},
				dialogFormVisible: false,
				multipleSelection: {},
				stuTopic:{
					id:null,
					stuId:null,
					topicId:null,
					score:null,
					state:null
				},
				userAct:{},
				user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
			}
		},
		created() {
			this.load()
		},
		methods: {
			load() {
				this.multipleSelection = null;
				const data = JSON.parse(sessionStorage.getItem("Act"));
				this.init = data;
				//获取当前活动的课题
				this.request.get("/teaTopic/findTopicByActId/" + data.id).then(res => {
					if (res.code == '200') {
						// this.$message.success("查询成功")
						this.tableData = res.data
					} else {
						this.$message.success("查询失败")
					}
				})
				//获取当前学生在该活动的选题状况
				this.request.get("/userActivity/state", {
					params: {
						pageNum: this.pageNum,
						pageSize: this.pageSize,
						actId: this.init.id,
						userId: this.user.username,
					}
				}).then(res => {
					this.userAct = res.data.records[0]
					this.state = res.data.records[0].state
					console.log(this.userAct)
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
			//判断选中数量
			selectAll(selection) {
				if (selection.length > 1) {
					selection.length = 1
				}
			},
			//只能选一项
			handleSelectionChange(selection) {
				if (selection.length > 1) {
					let del_row = selection.shift()
					this.$refs.table.toggleRowSelection(del_row, false)
				}
				this.multipleSelection = selection[0]
				console.log(this.multipleSelection)
			},
			//选题方法 添加数据到学生选题表 并且更新user_activity状态为1 即学生已选题
			choseTopic(){
				if(this.multipleSelection!=null){
					this.userAct.state=1
					//更新选题状态
					this.request.post("/userActivity", this.userAct).then(res => {
					  if (res.code === '200') {
					    this.load()
					  } else {
					    this.$message.error("保存失败")
					  }
					})
					//插入数据到选题表
					this.stuTopic.stuId = this.user.username
					this.stuTopic.topicId = this.multipleSelection.id
					this.request.post("/stuTopic", this.stuTopic).then(res => {
					  if (res.code === '200') {
					    this.load()
					  } else {
					    this.$message.error("保存失败")
					  }
					})
					this.$message.success("选题成功")
				}else{
					this.$message.warning("请选择课题")
				}
				
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
