<template>
	<div>
		<div id="head">
			<h1>{{init.name}}</h1>
		</div>
		<!-- 表格 -->
		<el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'">
			<el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
			<el-table-column prop="name" label="题目名称" width="180"></el-table-column>
			<el-table-column prop="des" label="题目描述"></el-table-column>
			<el-table-column label="文件" width="80">
				<template slot-scope="scope">
					<el-button type="primary" @click="download(scope.row.file)" v-if="scope.row.file">下载</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="nickname" label="申报人" width="100"></el-table-column>
			<el-table-column prop="status" label="审批状态" width="100" sortable>
				<template slot-scope="scope">
					<el-tag type="warning" effect="dark" v-if="scope.row.status==2">待审批</el-tag>
					<el-tag type="success" effect="dark" v-if="scope.row.status==1">已通过</el-tag>
					<el-tag type="danger" effect="dark" v-if="scope.row.status==0">未通过</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button type="primary" @click="check(scope.row)" v-if="scope.row.status==2">审核<i class="el-icon-thumb"></i></el-button>
					<el-button type="success" @click="details(scope.row)" v-if="scope.row.status!=2">查看详情 <i class="el-icon-info"></i></el-button>
				</template>
			</el-table-column>
		</el-table>
		<!-- 弹窗dialog -->
		<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
		  <el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
		    <el-form-item label="申报人">
		      <el-input v-model="form.nickname" autocomplete="off" :disabled="true"></el-input>
		    </el-form-item>
		    <el-form-item label="题目名称">
		      <el-input v-model="form.name" autocomplete="off" type="text" :disabled="true"></el-input>
		    </el-form-item>
		    <el-form-item label="题目描述">
		      <el-input v-model="form.des" autocomplete="off" type="textarea" :disabled="true"></el-input>
		    </el-form-item>
		    <el-form-item label="任务书">
		      <template slot-scope="scope">
		      	<el-button type="primary" @click="download(form.file)" v-if="form.file">下载</el-button>
		      </template>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-popconfirm
		        class="ml-5"
		        confirm-button-text='确定'
		        cancel-button-text='我再想想'
		        icon="el-icon-info"
		        icon-color="red"
		        title="您确定打回吗？"
		        @confirm="refuse()"
		    >
		      <el-button type="danger" slot="reference">拒绝 <i class="el-icon-remove-outline"></i></el-button>
		    </el-popconfirm>
			<el-popconfirm
			    class="ml-5"
			    confirm-button-text='确定'
			    cancel-button-text='我再想想'
			    icon="el-icon-info"
			    icon-color="red"
			    title="您确定通过吗？"
			    @confirm="saveTopic()"
			>
			  <el-button type="success" slot="reference">通过 <i class="el-icon-circle-check"></i></el-button>
			</el-popconfirm>
		  </div>
		</el-dialog>
		
		<!-- 弹窗dialog -->
		<el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
		  <el-form label-width="120px" size="small" style="width: 80%; margin: 0 auto">
		    <el-form-item label="申报人">
		      <el-input v-model="form.nickname" autocomplete="off" :disabled="true"></el-input>
		    </el-form-item>
		    <el-form-item label="题目名称">
		      <el-input v-model="form.name" autocomplete="off" type="text" :disabled="true"></el-input>
		    </el-form-item>
		    <el-form-item label="题目描述">
		      <el-input v-model="form.des" autocomplete="off" type="textarea" :disabled="true"></el-input>
		    </el-form-item>
		    <el-form-item label="任务书">
		      <template slot-scope="scope">
		      	<el-button type="primary" @click="download(form.file)" v-if="form.file">下载</el-button>
		      </template>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-popconfirm
		        class="ml-5"
		        confirm-button-text='确定'
		        cancel-button-text='我再想想'
		        icon="el-icon-info"
		        icon-color="red"
		        title="您确定打回吗？"
		        @confirm="refuse()"
		    >
		      <el-button type="danger" slot="reference">拒绝 <i class="el-icon-remove-outline"></i></el-button>
		    </el-popconfirm>
			<el-popconfirm
			    class="ml-5"
			    confirm-button-text='确定'
			    cancel-button-text='我再想想'
			    icon="el-icon-info"
			    icon-color="red"
			    title="您确定通过吗？"
			    @confirm="saveTopic()"
			>
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
				//init：id、name 活动id、活动名称
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
				//获取上一个页面传过来的活动id
				const data = JSON.parse(sessionStorage.getItem("Act"));
				this.init = data;
				this.request.get("/teaTopic/findApplyByActId/" + data.id).then(res => {
					if (res.code == '200') {
						// this.$message.success("查询成功")
						this.tableData = res.data
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
			},
			//教务审批课题 通过要加入课题表 不通过要更改tea_topic的status
			check(row){
				this.form = row
				this.dialogFormVisible = true
			},
			//通过课题新增至课题表并修改审批状态为已通过
			saveTopic(){
				//id actId
				const data ={}
				data.id = this.form.id
				data.actId = this.init.id
				//将该题目添加至活动课题表
				console.log(data)
				this.request.post("/activityTopic", data).then(res => {
				  if (res.code === '200') {
				    // this.$message.success("保存成功") 
				  } else {
				    this.$message.error("保存失败")
				  }
				})
				//更新题目审批状态status为1
				this.form.status = 1
				this.request.post("/teaTopic", this.form).then(res => {
				  if (res.code === '200') {
				    this.$message.success("操作成功")
				  } else {
				    this.$message.error("保存失败")
				  }
				})
				this.dialogFormVisible = false
				this.load()
			},
			//打回课题更改审批状态
			refuse(){
				//更新题目审批状态status为1
				this.form.status = 0
				this.request.post("/teaTopic", this.form).then(res => {
				  if (res.code === '200') {
				    this.$message.success("保存成功")
					this.dialogFormVisible = false
					this.load()
				  } else {
				    this.$message.error("保存失败")
				  }
				})
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
