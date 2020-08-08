<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">基金代码</label>
        <el-input v-model="query.fundcode" clearable placeholder="基金代码" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">更新时间</label>
        <el-input v-model="query.gztime" clearable placeholder="更新时间" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">最新估值</label>
        <el-input v-model="query.gszzl" clearable placeholder="最新估值" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">最新值</label>
        <el-input v-model="query.gsz" clearable placeholder="最新值" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">昨日净值</label>
        <el-input v-model="query.dwjz" clearable placeholder="昨日净值" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">净值日期</label>
        <el-input v-model="query.jzrq" clearable placeholder="净值日期" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">基金名称</label>
        <el-input v-model="query.name" clearable placeholder="基金名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">创建时间</label>
        <el-input v-model="query.createTime" clearable placeholder="创建时间" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="基金代码">
            <el-input v-model="form.fundcode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="form.gztime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="最新估值">
            <el-input v-model="form.gszzl" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="最新值">
            <el-input v-model="form.gsz" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="昨日净值">
            <el-input v-model="form.dwjz" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="净值日期">
            <el-input v-model="form.jzrq" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="基金名称">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="id" />
        <el-table-column prop="fundcode" label="基金代码" />
        <el-table-column prop="gztime" label="更新时间" />
        <el-table-column prop="gszzl" label="最新估值" />
        <el-table-column prop="gsz" label="最新值" />
        <el-table-column prop="dwjz" label="昨日净值" />
        <el-table-column prop="jzrq" label="净值日期" />
        <el-table-column prop="name" label="基金名称" />
        <el-table-column prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','jjRealTimeDynamic:edit','jjRealTimeDynamic:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudJjRealTimeDynamic from '@/api/jjRealTimeDynamic'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, fundcode: null, gztime: null, gszzl: null, gsz: null, dwjz: null, jzrq: null, name: null, createTime: null }
export default {
  name: 'JjRealTimeDynamic',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'jj/dynamic', url: 'api/jjRealTimeDynamic', sort: 'id,desc', crudMethod: { ...crudJjRealTimeDynamic }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'jjRealTimeDynamic:add'],
        edit: ['admin', 'jjRealTimeDynamic:edit'],
        del: ['admin', 'jjRealTimeDynamic:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'fundcode', display_name: '基金代码' },
        { key: 'gztime', display_name: '更新时间' },
        { key: 'gszzl', display_name: '最新估值' },
        { key: 'gsz', display_name: '最新值' },
        { key: 'dwjz', display_name: '昨日净值' },
        { key: 'jzrq', display_name: '净值日期' },
        { key: 'name', display_name: '基金名称' },
        { key: 'createTime', display_name: '创建时间' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
