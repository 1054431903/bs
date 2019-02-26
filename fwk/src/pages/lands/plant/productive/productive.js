import { Toast } from '@/models/toast'
import { excuteApis } from '@/apis'
import { CProductive } from '@/models/productive'
import { CPlant } from '@/models/plant'

export default {
  props: {
    curplant: {
      type: Object,
      require: true,
      default: () => {}
    },
    productiveid: {
      type: Number,
      require: true,
      default: null
    },
    addOrEditDialog: {
      type: String,
      require: true,
      default: 'IS_NONE'
    },
    viewDialog: {
      type: Boolean,
      require: true,
      default: false
    }
  },
  data () {
    const ADD_OR_EDIT = {
      'IS_EDIT': true,
      'IS_ADD': true,
      'IS_NONE': false
    }
    return {
      toast: null,
      theme: 'primary',
      addOrEdit: ADD_OR_EDIT,
      productive: null,
      plant: null,
      addOrEditPlantDialog: false,
      addOrEditDialogTitle: '',
      viewProductiveDialog: false,
      years: [],
      levels: [],
      units: [],
      precategorys: [],
      formValid: false,
      preoutputlabel: '预估产量',
      model: {
        id: null,
        plantid: null,
        year: new Date().getFullYear(),
        output: null,
        level: 'output-level-other',
        category: 'preestimate-per-mu-yeild',
        categoryname: null,
        preoutput: null,
        preoutputunit: null,
        preoutputunitname: null,
        preland: null,
        prelandunit: null,
        prelandunitname: null,
        actoutput: null,
        actoutputunit: null,
        actoutputunitname: null
      },
      rules: {
        required: [
          (v) => !!v || '必填项'
        ],
        samplnum: [
          (v) => !!v || '必填项',
          // (v) => !!v || v.length <= 20 || '长度不超过20个字符'
          (v) => this.sampleRule(v) || '长度不超过20个字符'
        ],
        orgname: [
          (v) => this.orgNameRule(v) || '长度不超过30个字符'
        ],
        soilgenre: [
          (v) => this.soilgenreRule(v) || '长度不超过20个字符'
        ],
        numbers: [
          (v) => this.numbersRule(v) || '请输入有效数字(小数保留2位),不超过1000000'
        ],
        breed: [
          (v) => this.breedRule(v) || '长度不超过20个字符'
        ],
        number: [
          (v) => this.numberRule(v) || '请输入有效整数,不超过1000000'
        ],
        introduce: [
          (v) => this.introduceRule(v) || '长度不超过500个字符'
        ]
      }
    }
  },
  methods: {
    sampleRule (v) {
      if (v) {
        return v.length <= 20
      }
      return true
    },
    orgNameRule (v) {
      if (v) {
        return v.length <= 30
      }
      return true
    },
    soilgenreRule (v) {
      if (v) {
        return v.length <= 20
      }
      return true
    },
    numbersRule (v) {
      if (v) {
        return (v > 0 && v <= 1000000) && ((/^\+?[1-9][0-9]*(\.\d{1,2})?$/.test(v)) || (/^0(\.\d{1,2})?$/.test(v)))
      }
      return true
    },
    numberRule (v) {
      if (v) {
        return (v > 0 && v <= 1000000) && (/^\+?[1-9][0-9]*$/.test(v))
      }
      return true
    },
    breedRule (v) {
      if (v) {
        return v.length <= 20
      }
      return true
    },
    introduceRule (v) {
      if (v) {
        return v.length <= 500
      }
      return true
    },
    // 查询产出物等级
    fetchLevels () {
      let requestParams = {
        dicttype: 'output-level'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data.length > 0) {
          this.levels = res.data
        }
      })
    },
    // 查询预估类别
    fetchPrecategorys () {
      let requestParams = {
        dicttype: 'preestimate-single-production-category'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.precategorys = res.data
        }
      })
    },
    // 查询产出物单位
    fetchUnits () {
      let requestParams = {
        dicttype: 'output-unit'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data.length > 0) {
          this.units = res.data
        }
      })
    },
    // 枚举产出时间
    fetchYears () {
      let now = new Date()
      let year = now.getFullYear() - 5
      this.years = []
      let i = 0
      while (i <= 10) {
        this.years.push(year + i)
        i++
      }
    },
    // 查询种植信息
    fetchPlant () {
      // if (!this.plantid) return
      // this.plant.setId(this.plantid)
      // this.plant.queryPlantById().then(response => {
      //   let res = response.data
      //   if (res.flag === 1 && res.data && res.data.length > 0) {
      //     let data = res.data[0]
      //     let p = new CPlant(data)
      //     this.plant.setArea(p.getArea())
      //     this.plant.setBreed(p.getBreed())
      //     this.plant.setBreedname(p.getBreedname())
      //     this.plant.setCategoryid(p.getCategoryid())
      //     this.plant.setCategoryname(p.getCategoryname())
      //     this.plant.setBegintime(p.getBegintime())
      //     this.plant.setPeroid(p.getPeroid())
      //     this.plant.setPeroidunit(p.getPeroidunit())
      //     this.plant.setAge(p.getAge())
      //     this.plant.setAgeunit(p.getAgeunit())
      //     this.plant.setBatch(p.getBatch())
      //     this.plant.setNumber(p.getNumber())
      //     this.plant.setNumberunit(p.getNumberunit())
      //     this.plant.setAttachments(p.getAttachments())
      //     this.plant.setIntroduce(p.getIntroduce())
      //     this.plant.setParcelid(p.getParcelid())
      //     this.$set(this.model, 'output', this.plant.getBreedname())
      //   }
      // })
      if (!this.curplant) return
      let p = this.curplant
      this.plant.setId(p.id)
      this.plant.setArea(p.area)
      this.plant.setBreed(p.breed)
      this.plant.setBreedname(p.breedname)
      this.plant.setCategoryid(p.parcelid)
      this.plant.setCategoryname(p.categoryname)
      this.plant.setBegintime(p.begintime)
      this.plant.setPeroid(p.peroid)
      this.plant.setPeroidunit(p.peroidunit)
      this.plant.setAge(p.age)
      this.plant.setAgeunit(p.ageunit)
      this.plant.setBatch(p.batch)
      this.plant.setNumber(p.number)
      this.plant.setNumberunit(p.numberunit)
      this.plant.setAttachments(p.attachments)
      this.plant.setIntroduce(p.introduce)
      this.plant.setParcelid(p.parcelid)
      this.$set(this.model, 'output', this.plant.getBreedname())
    },
    setProductive (p) {
      this.productive.setId(p['id'] || null)
      this.productive.setPlantid(p['plantid'] || null)
      this.productive.setYear(Number(p['outputyear']) || null)
      this.productive.setOutput(p['output'] || null)
      this.productive.setLevel(p['outputlevel'] || null)
      this.productive.setLevelname(p['outputlevelname'] || null)
      this.productive.setCategory(p['preestimatecategory'] || null)
      this.productive.setCategoryname(p['preestimatecategoryname'] || null)
      this.productive.setPreoutput(p['preestimateoutput'] || null)
      this.productive.setPreoutputunit(p['preestimateoutputunit'] || null)
      this.productive.setPreoutputunitname(p['preestimateoutputunitname'] || null)
      this.productive.setPreland(p['preestimatelandoutput'] || null)
      this.productive.setPrelandunit(p['preestimatelandoutputunit'] || null)
      this.productive.setPrelandunitname(p['preestimatelandoutputunitname'] || null)
      this.productive.setActoutput(p['actualoutput'] || null)
      this.productive.setActoutputunit(p['actualoutputunit'] || null)
      this.productive.setActoutputunitname(p['actualoutputunitname'] || null)
    },
    // 根据种植物ID及产出物ID查询产出信息详情
    async fetchProductive () {
      if (!this.productiveid) return
      this.productive.setId(this.productiveid)
      this.productive.setPlantid(this.curplant.id)
      let responseProductive = await this.productive.queryProductiveById()
      let resProductive = responseProductive.data
      if (resProductive.flag === 1) {
        let pd = resProductive.data[0]
        this.setProductive(pd)
        Object.assign(this.model, this.productive.getProductive())
      } else {
        console.log('获取产量信息失败.')
      }
    },
    cancel () {
      console.log('取消保存.')
      this.$emit('update:addOrEditDialog', 'IS_NONE')
    }
  },
  watch: {
    viewDialog (v) {
      this.viewProductiveDialog = v
      // v ? this.fetchPlant() : null
      v ? this.fetchProductive() : null
    },
    viewProductiveDialog (v) {
      this.$emit('update:viewDialog', v)
    }
  },
  created () {
    this.toast = new Toast(this, null, null)
    this.productive = new CProductive()
    this.plant = new CPlant()
    this.theme = global.helper.ls.get('theme')
  }
}
