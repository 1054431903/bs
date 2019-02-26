const globalVar = {
  // 资源访问地址
  mediumResourceUrl: 'http://118.190.81.136:80',
  mediumResourceRegUrl: /http:\/\/118.190.81.136:80\//,
  // 文件上传地址
  uploadFileUrl: '/fwk-service-archives/common/fileservice/upload?islogin=1',
  // base64数据文件上传地址
  uplaodBase64Url: '/fwk-service-archives/common/fileservice/base64/upload',
  // 默认文件类型
  fileMimeType: filename => {
    filename = filename.toLocaleUpperCase()
    let extname = filename.substring(
      filename.lastIndexOf('.') + 1,
      filename.length
    )
    if (extname === 'PNG' || extname === 'JPG' || extname === 'BMP') {
      return 'image'
    } else if (extname === 'MP4') {
      return 'video'
    } else {
      return extname
    }
  },

  commonApis: {
    baseUrl: '/fwk-service-archives/',
    common: {
      querydictlist: {
        url: '/dictservice/querydictlist',
        method: 'post'
      }
    }
  },

  // systemApiUrls 系统管理
  systemApis: {
    baseUrl: '/fwk-service-system/',
    information: {
      // 信息发布概要
      query: {
        url: '/queryInformation',
        method: 'post'
      },
      add: {
        url: '/saveInformation',
        method: 'post'
      },
      delete: {
        url: '/deleteInformation',
        method: 'post'
      },
      update: {
        url: '/updateInformation',
        method: 'post'
      },
      publish: {
        url: '/publishInformation',
        method: 'post'
      },
      copy: {
        url: '/copyInformation',
        method: 'post'
      },
      queryProductType: {
        url: '/queryProductType',
        method: 'post'
      }
    },
    informationContent: {
      // 信息发布详情
      query: {
        url: '/queryInformationContent',
        method: 'post'
      },
      add: {
        url: '/saveInformationContent',
        method: 'post'
      },
      delete: {
        url: '/deleteInformationContent',
        method: 'post'
      },
      update: {
        url: '/updateInformationContent',
        method: 'post'
      },
      updateSort: {
        url: '/updateSort',
        method: 'post'
      }
    },
    dict: {
      // 系统字典
      query: {
        url: '/queryTSdict',
        method: 'get'
      },
      save: {
        url: '/saveTSdict',
        method: 'post'
      },
      update: {
        url: '/updateTSdict',
        method: 'post'
      },
      delete: {
        url: '/deleteTSdict',
        method: 'post'
      }
    },
    tsdictType: {
      // 系统字典类型
      query: {
        url: '/queryTSdictType',
        method: 'get'
      }
    },
    position: {
      // 查询行政区划详情
      queryPositionDetailByCode: {
        url: '/queryPositionDetailByCode',
        method: 'get'
      }
    },
    varieties: {
      // 查询品种信息
      query: {
        url: '/queryVarieties',
        method: 'get'
      },
      save: {
        url: '/saveVarieties',
        method: 'post'
      },
      update: {
        url: '/updateVarieties',
        method: 'post'
      },
      delete: {
        url: '/deleteVarieties',
        method: 'post'
      }
    }
  },
  // archivesApiUrls 档案管理
  archivesApis: {
    baseUrl: '/fwk-service-archives/',
    common: {
      query: {
        url: '/dictservice/querydictlist',
        method: 'post'
      },
      uploadBase64: {
        url: '/fileservice/base64/upload',
        method: 'post'
      },
      upload: {
        url: '/fileservice/upload',
        method: 'post'
      },
      reversalQueryPosition: {
        url: '/querypositionlistasdictionary',
        method: 'get'
      },
      querypublicweather: {
        url: '/querypublicweather',
        method: 'post'
      },
      querypositionbycode: {
        url: '/querypositionbycode',
        method: 'post'
      }
    },
    position: {
      query: {
        url: '/queryPositionByParentCode',
        method: 'get'
      }
    },
    peasant: {
      // 合作农户档案
      query: {
        url: '/querylist',
        method: 'post'
      },
      queryById: {
        url: '/querybyid',
        method: 'post'
      },
      add: {
        url: '/add',
        method: 'post'
      },
      delete: {
        url: '/delete',
        method: 'post'
      },
      update: {
        url: '/update',
        method: 'post'
      }
    },
    peasantprotocol: {
      // 合作农户协议档案
      query: {
        url: '/querylist',
        method: 'post'
      },
      queryById: {
        url: '/querybyid',
        method: 'post'
      },
      add: {
        url: '/add',
        method: 'post'
      },
      delete: {
        url: '/delete',
        method: 'post'
      },
      update: {
        url: '/update',
        method: 'post'
      },
      peasantlist: {
        url: '/querypeasantlist',
        method: 'get'
      }
    }
  },
  // parcelApiUrls 地块
  parcelApis: {
    baseUrl: '/fwk-service-parcel/',
    parcel: {
      // 地块管理
      query: {
        url: '/queryParcel',
        method: 'post'
      },
      queryParcel: {
        url: '/queryParcelWithSimpleInfo',
        method: 'post'
      },
      add: {
        url: '/saveParcel',
        method: 'post'
      },
      delete: {
        url: '/deleteParcel',
        method: 'post'
      },
      update: {
        url: '/updateParcel',
        method: 'post'
      },
      queryParcelAreaStatsByAddressCode: {
        url: '/queryParcelAreaStatsByAddressCode',
        method: 'post'
      },
      batchSaveParcel: {
        url: '/batchSaveParcel',
        method: 'post'
      }
    },
    parcelPlant: {
      // 种植信息
      query: {
        url: '/queryParcelPlant',
        method: 'post'
      },
      add: {
        url: '/saveParcelPlant',
        method: 'post'
      },
      delete: {
        url: '/deleteParcelPlant',
        method: 'post'
      },
      update: {
        url: '/updateParcelPlant',
        method: 'post'
      }
    },
    parcelMeasureSoil: {
      // 测土记录
      query: {
        url: '/queryMeasureSoil',
        method: 'post'
      },
      add: {
        url: '/saveMeasureSoil',
        method: 'post'
      },
      delete: {
        url: '/deleteMeasureSoil',
        method: 'post'
      },
      update: {
        url: '/updateMeasureSoil',
        method: 'post'
      },
      queryMeasureSoilStat: {
        url: '/queryMeasureSoilStat',
        method: 'post'
      }
    },
    parcelCoord: {
      // 地块坐标
      query: {
        url: '/queryParcelCoord',
        method: 'post'
      },
      add: {
        url: '/saveParcelCoord',
        method: 'post'
      },
      addBatch: {
        url: '/batchSaveParcelCoord',
        method: 'post'
      },
      delete: {
        url: '/batchDeleteParcelCoord',
        method: 'post'
      },
      deleteBatch: {
        url: '/batchDeleteParcelCoord',
        method: 'post'
      },
      update: {
        url: '/updateParcelCoord',
        method: 'post'
      },
      updateBatch: {
        url: '/batchUpdateParcelCoord',
        method: 'post'
      }
    },
    parcelPlantYield: {
      // 产量信息
      query: {
        url: '/queryParcelPlantYield',
        method: 'post'
      },
      add: {
        url: '/saveParcelPlantYield',
        method: 'post'
      },
      delete: {
        url: '/deleteParcelPlantYield',
        method: 'post'
      },
      update: {
        url: '/updateParcelPlantYield',
        method: 'post'
      }
    }
  },
  // userApisUrls 用户
  userApisUrls: {
    baseUrl: '/fwk-service-user/',
    user: {
      // 地块管理
      addOrUpdate: {
        url: '/saveUser',
        method: 'get'
      },
      updatePassword: {
        url: '/updatePassword',
        method: 'get'
      },
      queryUserByOrgDepartment: {
        url: '/queryUserByOrgDepartment',
        method: 'get'
      },
      queryUserAndDepartByLoginUserOrg: {
        url: '/queryUserAndDepartByLoginUserOrg',
        method: 'get'
      }, // 树
      queryUserInfoById: {
        url: '/queryUserInfoById',
        method: 'get'
      },
      login: {
        url: '/login',
        method: 'get'
      },
      regist: {
        url: '/regist',
        method: 'post'
      },
      updatePasswordByPhone: {
        url: '/updatePasswordByPhone',
        method: 'post'
      },
      checkUserIsOwen: {
        url: '/checkUserIsOwen',
        method: 'post'
      },
      removeUserFromOrg: {
        url: '/removeUserFromOrg',
        method: 'post'
      },
      updatePhoneById: {
        url: '/updatePhoneById',
        method: 'post'
      },
      queryUserByLoginUserOrg: {
        url: '/queryUserByLoginUserOrg',
        method: 'post'
      },
      getDefaultOrgByPhone: {
        url: '/getDefaultOrgByPhone',
        method: 'post'
      },
      initPermission: {
        url: '/initPermission',
        method: 'post'
      }
    },
    depart: {
      queryDepartmentByLoginOrgId: {
        url: '/queryDepartmentByLoginOrgId',
        method: 'get'
      },
      deleteDepartment: {
        url: '/deleteDepartment',
        method: 'post'
      },
      queryDepartmentDetail: {
        url: '/queryDepartmentDetail',
        method: 'post'
      },
      saveDepartment: {
        url: '/saveDepartment',
        method: 'post'
      }
    },
    pub: {
      OrgDepartUserTree: {
        url: '/OrgDepartUserTree',
        method: 'get'
      }
    },
    role: {
      queryRole: {
        url: '/queryRole',
        method: 'post'
      },
      deleteRole: {
        url: '/deleteRole',
        method: 'post'
      },
      saveRole: {
        url: '/saveRole',
        method: 'post'
      },
      queryPermissionTree: {
        url: '/queryPermissionTree',
        method: 'post'
      }
    },
    verificode: {
      getIdentifycode: {
        url: '/sendSMS',
        method: 'post'
      },
      checkVerificode: {
        url: '/checkVerificode',
        method: 'post'
      }
    },
    org: {
      improveOrgInfo: {
        url: '/saveOrg',
        method: 'post'
      },
      queryOrgByOperateid: {
        url: '/queryOrgByOperateid',
        method: 'post'
      },
      deleteOrg: {
        url: '/deleteOrg',
        method: 'get'
      },
      turnOrg: {
        url: '/turnOrg',
        method: 'post'
      }
    }
  },

  /**
   * 农事相关接口
   */
  farmApiUrls: {
    baseUrl: '/fwk-service-farm/',
    record: {
      querylist: {
        url: '/querylist',
        method: 'post'
      },
      querybyid: {
        url: '/querybyid',
        method: 'post'
      },
      add: {
        url: '/add',
        method: 'post'
      },
      deletebyid: {
        url: '/deletebyid',
        method: 'post'
      },
      update: {
        url: '/update',
        method: 'post'
      }
    },
    task: {
      add: {
        url: '/add',
        method: 'post'
      },
      update: {
        url: '/update',
        method: 'post'
      },
      savetorecord: {
        url: '/savetorecord',
        method: 'post'
      },
      querylist: {
        url: '/querylist',
        method: 'post'
      },
      querybyid: {
        url: '/querybyid',
        method: 'post'
      },
      deletebyid: {
        url: '/deletebyid',
        method: 'post'
      },
      querytaskdynamic: {
        url: '/querytaskdynamic',
        method: 'post'
      },
      confirmtask: {
        url: '/confirmtask',
        method: 'post'
      },
      addtaskcomment: {
        url: '/addtaskcomment',
        method: 'post'
      },
      updateasfinish: {
        url: '/updateasfinish',
        method: 'post'
      }
    },
    plan: {
      querylist: {
        url: '/querylist',
        method: 'post'
      },
      querybyid: {
        url: '/querybyid',
        method: 'post'
      },
      add: {
        url: '/add',
        method: 'post'
      },
      update: {
        url: '/update',
        method: 'post'
      },
      deletebyid: {
        url: '/deletebyid',
        method: 'post'
      }
    },
    communion: {
      // http://10.88.20.66:5763/fwk-service-farm/communion/commentcontent/deletebyid
      add: {
        url: '/add',
        method: 'post'
      },
      commentdeletebyid: {
        url: '/commentcontent/deletebyid',
        method: 'post'
      },
      reversiondeletebyid: {
        url: '/commentcontentreversion/deletebyid',
        method: 'post'
      },
      querylist: {
        url: '/querylist',
        method: 'post'
      },
      querybyid: {
        url: '/querybyid',
        method: 'post'
      },
      commentcontentadd: {
        url: '/commentcontent/add',
        method: 'post'
      },
      querylistbycommunionid: {
        url: '/commentcontent/querylistbycommunionid',
        method: 'post'
      },
      commentreversionadd: {
        url: '/commentreversion/add',
        method: 'post'
      },
      querylistbycommunioncommentid: {
        url: '/commentcontentreversion/querylistbycommunioncommentid',
        method: 'post'
      }
    }
  },

  /**
   * 农业知识库相关接口
   */
  repositoryApis: {
    baseUrl: 'http://47.106.248.12:9401/',
    rent: {
      queryArticle: {
        url: '/content/queryContentList',
        method: 'get'
      }, // 知识库文章
      queryArticleDetail: {
        url: '/content/queryContentDetail',
        method: 'get'
      }, // 知识库文章详情
      queryArticleBykeycord: {
        url: '/content',
        method: 'get'
      }, // 通过关键词搜索知识
      queryCorrection: {
        url: '/content/autocomplete',
        method: 'get'
      } // 关键词纠错
    }
  },

  // commonApiUrls 通用接口
  commonApiUrls: {
    baseUrl: '/fwk-service-system/',
    poslatlng: {
      // 通过行政区名称查询经纬度
      query: {
        url: '/query',
        method: 'get'
      }
    },
    category: {
      // 查询产品分类
      query: {
        url: '/queryCategory',
        method: 'get'
      }
    },
    common: {
      // 查询墨迹天气
      queryMoJiWeather: {
        url: '/queryMoJiWeather',
        method: 'post'
      }
    }
  },
  testApisUrls: {
    baseUrl: '',
    jd: {
      index: {
        url: '',
        method: 'get'
      }
    }
  },
  // pusherApisUrls 消息推送
  pusherApisUrls: {
    baseUrl: '/fwk-service-pusher/',
    task: {
      approval: {
        url: '/noticeWaitingForApproval',
        method: 'get',
        paramstype: 'path'
      },
      execution: {
        url: '/noticeWaitingForExecution',
        method: 'get',
        paramstype: 'path'
      },
      start: {
        url: '/noticeNearTheStart',
        method: 'get',
        paramstype: 'path'
      },
      end: {
        url: '/noticeNearTheEnd',
        method: 'get',
        paramstype: 'path'
      }
    },
    planContent: {
      execution: {
        url: '/noticeWaitingForExecution',
        method: 'get',
        paramstype: 'path'
      },
      start: {
        url: '/noticeNearTheStart',
        method: 'get',
        paramstype: 'path'
      },
      end: {
        url: '/noticeNearTheEnd',
        method: 'get',
        paramstype: 'path'
      }
    }
  },
  TASK_STATE: {
    已逾期: {
      name: 'OVERDUE'
    },
    进行中: {
      name: 'UNDER_WAY'
    },
    即将开始: {
      name: 'READIED'
    },
    已完成: {
      name: 'DONE'
    },
    已考核: {
      name: 'CHECKED'
    },
    待考核: {
      name: 'UNCHECKED'
    },
    已中止: {
      name: 'FINISHED'
    },
    待审批: {
      name: 'UNAPPROVE'
    },
    审批通过: {
      name: 'APPROVE_ACCEPTED'
    },
    审批不通过: {
      name: 'APPROVE_REJECTED'
    },
    未知状态: {
      name: 'UNKNOWN'
    }
  },
  TASK_STAGES: {
    FINISHED: {
      NAME: '已中止',
      LABEL: `static/task/yizhongzhi.png`,
      TODO: []
    },
    CHECKED: {
      NAME: '已考核',
      LABEL: `static/task/yikaohe.png`,
      TODO: []
    },
    UNCHECKED: {
      NAME: '待考核', // ==> 已完成
      LABEL: null,
      TODO: ['check']
    },
    APPROVE_ACCEPTED: {
      NAME: '审批通过',
      LABEL: null,
      TODO: []
    },
    APPROVE_REJECTED: {
      NAME: '审批不通过',
      LABEL: `static/task/butongg.png`,
      TODO: []
    },
    UNAPPROVE: {
      NAME: '待审批',
      LABEL: null,
      TODO: ['finish', 'accept', 'reject']
    },
    UNDER_WAY: {
      NAME: '进行中',
      LABEL: null,
      TODO: ['finish', 'done']
    },
    OVERDUE: {
      NAME: '已逾期',
      LABEL: null,
      TODO: ['finish', 'done']
    },
    READIED: {
      NAME: '即将开始',
      LABEL: null,
      TODO: ['finish', 'done']
    },
    DONE: {
      NAME: '已完成',
      LABEL: `static/task/yiwancheng.png`,
      TODO: ['examine']
    },
    UNKNOWN: {
      NAME: '未知状态',
      LABEL: null,
      TODO: []
    }
  },
  EXTNAMES: {
    images: {
      suffix: ['PNG', 'JPG', 'JPEG', 'BMP', 'TIF', 'GIF'],
      icon: '/static/task/tupian.png',
      type: 'custom'
    },
    videos: {
      suffix: ['MP4'],
      icon: '/static/task/shipin.png',
      type: 'custom'
    },
    voices: {
      suffix: ['MP3'],
      icon: '/static/task/huatong.png',
      type: 'custom'
    },
    words: {
      suffix: ['DOC', 'DOCX'],
      icon: '/static/task/wenjian.png',
      type: 'custom'
    },
    excels: {
      suffix: ['XLS', 'XLSX'],
      icon: '/static/task/wenjian.png',
      type: 'custom'
    },
    pdfs: {
      suffix: ['PDF'],
      icon: '/static/task/wenjian.png',
      type: 'custom'
    },
    others: {
      suffix: ['TXT'],
      icon: '/static/task/wenjian.png',
      type: 'custom'
    }
  }
}
export default globalVar
