import { Toast } from '@/models/toast'
import { excuteApis } from '@/apis'
import { CPlant } from '@/models/plant'
import { CProductive } from '@/models/productive'

export default {
  props: {
    parcelid: {
      type: Number,
      require: true,
      default: null
    },
    plantid: {
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
    // 种植信息单位定义
    const GROW_PEROIDS = ['年', '月', '日'] // 生长周期
    const PLANT_AGES = ['年', '月', '日'] // 株龄
    const PLANT_NUMBER = ['株', '棵', '盆'] // 株数
    return {
      toast: null,
      theme: 'primary',
      addOrEdit: ADD_OR_EDIT,
      plant: null,
      productive: null,
      productiveid: 0,
      plantapis: null,
      model: {
        id: null,
        parcelid: null,
        categoryid: null,
        categoryname: null,
        breed: null,
        breedname: null,
        batch: null,
        begintime: null,
        peroid: null,
        peroidunit: '年',
        age: null,
        ageunit: null,
        area: null,
        number: null,
        numberunit: null,
        products: [],
        introduce: null,
        attachments: []
      },
      addOrEditPlantDialog: false,
      addOrEditDialogTitle: '',
      viewPlantDialog: false,
      viewPlantDialogTitle: '种植信息详情',
      plantFormValid: false,
      addOrEditProductiveDialog: '',
      viewProductiveDialog: false,
      productkinds: [],
      producttypes: [],
      peroidunits: GROW_PEROIDS,
      ageunits: PLANT_AGES,
      numberunits: PLANT_NUMBER,
      precategorys: {},
      products: [],
      productcolumns: [
        {
          text: '产出年份',
          align: 'left',
          sortable: false,
          value: 'year'
        },
        {
          text: '产出物',
          align: 'left',
          sortable: false,
          value: 'output'
        },
        {
          text: '产出物等级',
          align: 'left',
          sortable: false,
          value: 'level'
        },
        {
          text: '预估单产产量',
          align: 'left',
          sortable: false,
          value: 'preoutput'
        },
        {
          text: '预估地块产量',
          align: 'left',
          sortable: false,
          value: 'preland'
        },
        {
          text: '实际产量',
          align: 'left',
          sortable: false,
          value: 'actoutput'
        },
        {
          text: '操作',
          align: 'center',
          sortable: false,
          value: ''
        }
      ],
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
          (v) => this.numbersRule(v) || '请输入有效数字(小数保留2位),大于0不超过1000000'
        ],
        breed: [
          (v) => this.breedRule(v) || '长度不超过20个字符'
        ],
        number: [
          (v) => this.peroidRule(v) || '请输入有效整数,不超过1000000'
        ],
        introduce: [
          (v) => this.introduceRule(v) || '长度不超过500个字符'
        ],
        batch: [
          (v) => this.batchRule(v) || '请输入中英文下划线及横杠等字符,长度不超50'
        ],
        peroid: [
          (v) => this.peroidRule(v) || '请输入有效整数,不超过1000000'
        ],
        age: [
          (v) => this.peroidRule(v) || '请输入有效整数,不超过1000000'
        ]
      },
      prompt: {
        id: '',
        data: null,
        dialog: false,
        title: '提示信息',
        body: '提示信息内容',
        ok: true
      },
      operateFlag: 'tips'
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
      return false
    },
    numberRule (v) {
      if (v) {
        return (v >= 0 && v <= 1000000) && (/^\+?[1-9][0-9]*$/.test(v))
      }
      return false
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
    // 验证批次
    batchRule (v) {
      if (v) {
        return (v.length > 0 && v.length <= 50) && (/^[A-Za-z0-9\u4e00-\u9fa5_-]+$/).test(v)
      }
      return true
    },
    // 生长周期
    peroidRule (v) {
      if (v) {
        return (v >= 0 && v <= 1000000) && /^(\d|[1-9]\d)*$/.test(v)
      }
      return true
    },
    // 植株数量
    plantNumberRule (v) {
      if (v) {
        return (v >= 0 && v <= 1000000) && /^(\d|[1-9]\d)*$/.test(v)
      }
      return false
    },
    // 查询品类
    fetchProductType () {
      excuteApis({}, global.constant.commonApiUrls, 'category', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.producttypes = res.data
        }
      })
    },
    // 查询品种
    fetchProductKind () {
      if (!this.model.categoryid) return
      let requestParams = {}
      Object.assign(requestParams, {categoryid: this.model.categoryid})
      excuteApis(requestParams, global.constant.systemApis, 'varieties', 'query').then(response => {
        let res = response.data
        if (res.flag === 1 && res.data.length > 0) {
          this.productkinds = res.data
          this.model.breed ? null : (this.model.breed = this.productkinds[0].id)
        } else {
          this.productkinds = []
          this.model.breed = null
        }
      })
    },
    showPlantType (categoryid) {
      let type = this._.find(this.producttypes, ['id', categoryid])
      return type ? type.name : '——'
    },
    // 查询预估类别
    fetchPrecategorys () {
      let requestParams = {
        dicttype: 'preestimate-single-production-category'
      }
      excuteApis(requestParams, global.constant.archivesApis, 'common', 'query').then(data => {
        let res = data.data
        if (res.flag === 1 && res.data.length > 0) {
          this.precategorys = []
          res.data.forEach(item => {
            let precategory = {}
            precategory[item.dictvalue] = item.dictname
            this.precategorys.push(Object.assign({}, precategory))
          })
        }
      })
    },
    setPlant (p) {
      this.plant.setArea(p['area'] || null)
      this.plant.setBreed(p['varieties'] || null)
      this.plant.setBreedname(p['varietiesname'] || null)
      this.plant.setCategoryid(p['categoryid'] || null)
      this.plant.setCategoryname(p['categoryname'] || null)
      this.plant.setBegintime((p && p['plantbegintime']) ? p['plantbegintime'].substr(0, 10) : null)
      this.plant.setPeroid(p['preestimategrowthcycle'])
      this.plant.setPeroidunit((p && p['preestimategrowthcycleunit']) ? p['preestimategrowthcycleunit'] : '年')
      this.plant.setAge(p['plantage'])
      this.plant.setAgeunit((p && p['plantageunit']) ? p['plantageunit'] : '年')
      this.plant.setBatch(p['growingbatch'] || null)
      this.plant.setNumber(p['number'])
      this.plant.setNumberunit((p && p['unit']) ? p['unit'] : '株')
      this.plant.setAttachments(p['attachments'] || [])
      this.plant.setIntroduce(p['introduce'] || null)
      this.plant.setParcelid(p['parcelid'] || null)
    },
    setProductives (pds) {
      this.products = []
      pds.forEach(pd => {
        let productive = new CProductive(pd)
        this.products.push(productive.getProductive())
      })
    },
    // 根据种植物ID查询此种植物详情
    async fetchPlant () {
      let id = this.plant.getId()
      if (!id) return
      let responsePlant = await this.plant.queryPlantById()
      let resPlant = responsePlant.data
      if (resPlant.flag === 1) {
        let p = resPlant.data[0] || {}
        this.setPlant(p)
      } else {
        console.log('获取种植信息失败.')
      }
      this.productive.setPlantid(id)
      let responseProductive = await this.productive.queryProdcutive()
      let resProductive = responseProductive.data
      if (resProductive.flag === 1) {
        let pds = resProductive.data
        // 初始化产量信息表格
        this.setProductives(pds)
        this.plant.setProducts(this.products)
        Object.assign(this.model, this.plant.getPlant())
        // console.log(this.model) //
        let title = (this.model['categoryname'] || '').concat('—', this.model['breedname'] || '')
        title ? (this.viewPlantDialogTitle = title) : '查看种植信息详情'
        // 动态设置表头(预估类别)
        // this.productcolumns[3].text = (this.products[0] && this.products[0].categoryname) ? this.products[0].categoryname : '预估产量'
      } else {
        console.log('获取产量信息失败.')
      }
    },
    cancel () {
      this.$emit('update:addOrEditDialog', 'IS_NONE')
    },
    // 计算预估单产产量内容
    showPreoutput (preoutput, unitname, precategory) {
      let res = '--'
      if (!preoutput) return res
      res = preoutput + ' ' + unitname + '/' + (precategory === 'preestimate-per-mu-yeild' ? '亩' : '株')
      return res
    }
  },
  watch: {
    plantid (v) {
      v ? this.plant.setId(v) : null
      v ? this.productive.setPlantid(v) : null // 初始化种植信息ID
    },
    parcelid (v) {
      console.log('parcelid: ' + v)
      v ? this.plant.setParcelid(v) : null // 初始化种植信息ID
    },
    viewDialog (v) {
      this.viewPlantDialog = v
      v ? this.fetchPlant() : null
    },
    viewPlantDialog (v) {
      this.$emit('update:viewDialog', v)
    },
    addOrEditProductiveDialog (v) {
      v === 'IS_NONE' ? this.fetchPlant() : null
    },
    'model.categoryid' (v) {
      v ? this.fetchProductKind() : null
    }
  },
  created () {
    this.toast = new Toast(this, null, null)
    this.plant = new CPlant()
    this.productive = new CProductive()
    this.theme = global.helper.ls.get('theme')
  }
}
