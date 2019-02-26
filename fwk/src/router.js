import Vue from 'vue'
import Router from 'vue-router'

function route (path, file, name, meta, children, redirect) {
  return {
    exact: true,
    path,
    name,
    children,
    meta,
    component: require(`./pages/${file}.vue`),
    redirect
  }
}

Vue.use(Router)

const router = new Router({
  base: __dirname,
  mode: 'hash',
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [
    route('/', 'regAndLogin/login', null, null, null, '/login'),
    route('/login', 'regAndLogin/login', 'login', {
      name: '登陆'
    }),
    route('/register', 'regAndLogin/register', 'register', {
      name: '注册'
    }),
    route('/resetPwdSuc', 'regAndLogin/resetPwdSuccessTips', 'resetPwdSuc', {
      name: '重置密码'
    }),
    route('/forgetPwd', 'regAndLogin/forgetPwd', 'forgetPwd', {
      name: '忘记密码'
    }),
    route('/improveOrgInfo', 'regAndLogin/improveOrgInfo', 'improveOrgInfo', {
      name: '完善组织信息'
    }),
    route('/error', 'errors/Error', 'error'),
    // path, file(*.vue), name, meta, children
    route('/home', 'Main', null, null, [
      route('main', 'home/Home', 'home', {
        name: '首页',
        breadNumber: 1
      }),
      route('chat/main', 'home/Chat', 'chat', {
        name: '农事交流',
        breadNumber: 2
      }), // 农事交流
      route('chat/ask', 'home/Ask', 'ask', {
        name: '农事提问',
        breadNumber: 3
      }), // 农事提问
      route('chat/detail', 'home/ChatDetail', 'chatDetail', {
        name: '农事交流详情',
        breadNumber: 3
      }), // 农事交流详情
      route(
        '/changeOrgInfo',
        'system/organizations/ChangeOrgInfo',
        'changeOrgInfo', {
          name: '切换组织信息'
        }
      )
    ]),
    route('/report', 'Main', null, null, [
      route('main', 'report/dashboard/Dashboard', 'dashboard', {
        name: '地块分布',
        breadNumber: 1
      }),
      route('taskreport', 'report/taskreport/TaskReport', 'taskreport', {
        name: '农事任务统计',
        breadNumber: 1
      })
    ]),
    route('/monitor', 'Main', null, null, [
      route('', 'monitor/Gis', 'monitor', {
        name: '物联网监管',
        breadNumber: 1
      })
    ]),
    route('/lands', 'Main', null, null, [
      route('main', 'lands/Lands', 'lands', {
        name: '地块管理',
        breadNumber: 1
      }),
      route('addland', 'lands/LandsAddForm', 'addland', {
        name: '添加地块',
        breadNumber: 2
      }),
      route('editland', 'lands/LandsEditForm', 'editland', {
        name: '编辑地块',
        breadNumber: 2
      }),
      route('viewland', 'lands/LandsViewForm', 'viewland', {
        name: '详情',
        breadNumber: 2
      }),
      route(
        'successlandstips',
        'lands/SuccessLandsTipsForm',
        'successlandstips', {
          name: '提示',
          breadNumber: 2
        }
      ),
      route('drawland', 'lands/DrawProfileForm', 'drawland', {
        name: '手工绘制地形',
        breadNumber: 2
      })
    ]),
    route('/repository', 'Main', null, null, [
      route('main', 'repository/Repository', 'repository', {
        name: '农业知识库',
        breadNumber: 1
      }), // 农业知识库
      route('detail', 'repository/RepositoryDetail', 'repositoryDetail', {
        name: '文章详情',
        breadNumber: 2
      }) // 农业知识库详情
    ]),
    route('/sop', 'Main', null, null, [
      route('', 'sop/FarmerSOP', 'sop', {
        name: '物候期管理规程',
        breadNumber: 1
      })
    ]),
    route('/archives', 'Main', null, null, [
      route(
        'farmerarchives/main',
        'archives/cooperatefarmer/FarmerArchives',
        'farmerarchives', {
          name: '合作农户档案管理',
          breadNumber: 1
        }
      ),
      route(
        'farmerarchives/addfarmer',
        'archives/cooperatefarmer/FarmerArchivesCreateForm',
        'addfarmer', {
          name: '新增',
          breadNumber: 2
        }
      ),
      route(
        'farmerarchives/successfarmertips',
        'archives/cooperatefarmer/SuccessFarmerTipsForm',
        'successfarmertips', {
          name: '提示',
          breadNumber: 3
        }
      ),
      route(
        'farmerarchives/editfarmer',
        'archives/cooperatefarmer/FarmerArchivesEditForm',
        'editfarmer', {
          name: '编辑',
          breadNumber: 2
        }
      ),
      route(
        'farmerarchives/viewfarmer',
        'archives/cooperatefarmer/FarmerArchivesViewForm',
        'viewfarmer', {
          name: '详情',
          breadNumber: 2
        }
      ),
      route(
        'farmeragreements/main',
        'archives/farmeragreements/FarmerAgreements',
        'farmeragreements', {
          name: '合作农户协议管理',
          breadNumber: 1
        }
      ),
      route(
        'farmeragreements/addagreement',
        'archives/farmeragreements/FarmerAgreementsCreateForm',
        'addagreement', {
          name: '新增',
          breadNumber: 2
        }
      ),
      route(
        'farmeragreements/editagreement',
        'archives/farmeragreements/FarmerAgreementsEditForm',
        'editagreement', {
          name: '编辑',
          breadNumber: 2
        }
      ),
      route(
        'farmeragreements/viewagreement',
        'archives/farmeragreements/FarmerAgreementViewForm',
        'viewagreement', {
          name: '详情',
          breadNumber: 2
        }
      ),
      route(
        'farmeragreements/successagreementtips',
        'archives/farmeragreements/SuccessAgreementTipsForm',
        'successagreementtips', {
          name: '提示',
          breadNumber: 3
        }
      ),
      route(
        'fatmedicinesprovide',
        'archives/materialsprovider/FatMedicinesProvide',
        'fatmedicinesprovide', {
          name: '农资发放'
        }
      ),
      route(
        'fatmedicinesprovide/sendrecord',
        'archives/materialsprovider/SendRecord',
        'sendrecord', {
          name: '新增'
        }
      ),
      route(
        'fatmedicinesprovide/sendrecordedit',
        'archives/materialsprovider/SendRecordEdit',
        'sendrecordedit', {
          name: '编辑'
        }
      )
    ]),
    route('/farmworks', 'Main', null, null, [
      route('farmerplans', 'farmworks/workplan/FarmerPlans2', 'farmerplans', {
        name: '农事计划管理',
        breadNumber: 1
      }),
      route(
        'farmertasks/main',
        'farmworks/worktask/FarmerTasks',
        'farmertasks', {
          name: '农事任务管理',
          breadNumber: 1
        }
      ),
      route(
        'farmertasks/farmertasksAdd',
        'farmworks/worktask/FarmerTasksAdd',
        'farmertasksAdd', {
          name: '添加',
          breadNumber: 2
        }
      ), // 添加
      route(
        'farmertasks/farmertasksEdit',
        'farmworks/worktask/FarmerTasksAdd',
        'farmertasksEdit', {
          name: '编辑',
          breadNumber: 2
        }
      ), // 编辑
      route(
        'farmertasks/farmertasksDetail',
        'farmworks/worktask/FarmerTasksDetail',
        'farmertasksDetail', {
          name: '详情',
          breadNumber: 2
        }
      ), // 详情
      route(
        'farmerrecords/main',
        'farmworks/workrecord/FarmerRecords',
        'farmerrecords', {
          name: '农事记录管理',
          breadNumber: 1
        }
      ),
      route(
        'farmerrecords/farmerrecordsAdd',
        'farmworks/workrecord/FarmerRecordsOperate',
        'farmerRecordsAdd', {
          name: '添加',
          breadNumber: 2
        }
      ), // 添加
      route(
        'farmerrecords/farmerrecordsEdit',
        'farmworks/workrecord/FarmerRecordsOperate',
        'farmerRecordsEdit', {
          name: '编辑',
          breadNumber: 2
        }
      ), // 编辑
      route(
        'farmerrecords/farmerrecordsDetail',
        'farmworks/workrecord/FarmerRecordsDetail',
        'farmerRecordsDetail', {
          name: '详情',
          breadNumber: 2
        }
      ) // 详情
      // route('/planttroubles', 'farmworks/planttroubles/PlantTroubles', 'planttroubles', { name: '病虫害管理' })
    ]),
    route('/system', 'Main', null, null, [
      route(
        'organizations/main',
        'system/organizations/Organizations',
        'organizations', {
          name: '组织管理',
          breadNumber: 1
        }
      ),
      route(
        'organizations/editorg',
        'system/organizations/OrgEdit',
        'editorg', {
          name: '编辑',
          breadNumber: 2
        }
      ),
      route(
        'organizations/editorgsuccess',
        'system/organizations/EditOrgSuccess',
        'editorgsuccess', {
          name: '提示',
          breadNumber: 3
        }
      ),
      route('memberManage', 'system/users/MemberManage', 'MemberManage', {
        name: '成员管理',
        breadNumber: 1
      }),
      // route('role', 'system/roles/Roles', 'roles', { name: '角色管理', breadNumber: 1 }),
      // route('rolesadd', 'system/roles/RolesAdd', 'rolesadd', { name: '添加角色', breadNumber: 2 }),
      // route('roleslook', 'system/roles/RolesLook', 'roleslook', { name: '角色详情', breadNumber: 2 }),
      // route('rolesedit', 'system/roles/RolesEdit', 'rolesedit', { name: '编辑角色', breadNumber: 2 }),
      route(
        'infopublish/main',
        'system/infopublish/SopPublish',
        'infopublish', {
          name: '物候期管理规程发布',
          breadNumber: 1
        }
      ),
      route('infopublish/sopadd', 'system/infopublish/SopAddForm', 'sopadd', {
        name: '新增',
        breadNumber: 2
      }),
      route(
        'infopublish/sopedit',
        'system/infopublish/SopEditForm',
        'sopedit', {
          name: '编辑',
          breadNumber: 2
        }
      ),
      route(
        'infopublish/sopview',
        'system/infopublish/SopViewForm',
        'sopview', {
          name: '详情',
          breadNumber: 2
        }
      ),
      route(
        'infopublish/sopsuccesstips',
        'system/infopublish/SopSuccessTipsForm',
        'sopsuccesstips', {
          name: '提示',
          breadNumber: 3
        }
      ),
      route('sysdictionary', 'system/dictionary/SortManage', 'sysdictionary', {
        name: '字典管理',
        breadNumber: 1
      }),
      // route('sortmanage', 'system/dictionary/SortManage', 'sortmanage', { name: '字典列表', breadNumber: 2 }),
      route('sortadd', 'system/dictionary/SortAdd', 'sortadd', {
        name: '新增',
        breadNumber: 2
      }),
      route('sortlook', 'system/dictionary/SortLook', 'sortlook', {
        name: '详情',
        breadNumber: 2
      }),
      route('sortedit', 'system/dictionary/SortEdit', 'sortedit', {
        name: '编辑',
        breadNumber: 2
      }),
      route('roleManage', 'system/roles/Role', 'roleManage', {
        name: '角色管理',
        breadNumber: 1
      }),
      route('authority', 'system/authority/Authority', 'authority', {
        name: '权限管理',
        breadNumber: 1
      })
    ]),
    route('/personalCenter', 'Main', null, null, [
      route(
        'organizeinfo',
        'personalcenter/organize/organizeInfo',
        'organizeInfo', {
          name: '组织信息',
          breadNumber: 1
        }
      ),
      route(
        'personaldata/main',
        'personalcenter/person/personalData',
        'personalData', {
          name: '个人资料',
          breadNumber: 1
        }
      ),
      route(
        'personalData/edit',
        'personalcenter/person/personalDataEdit',
        'personalDataEdit', {
          name: '编辑个人资料',
          breadNumber: 2
        }
      ),
      route(
        'personalData/successtips',
        'personalcenter/person/personalDataEditSuccessTips',
        'personalDataEditSuccessTips', {
          name: '提示',
          breadNumber: 3
        }
      ),
      route(
        'accountsettings',
        'personalcenter/account/accountSettings',
        'accountSettings', {
          name: '账号设置'
        }
      ),
      route('main', 'personalcenter/personalCenter', 'personalCenter', {
        name: '个人中心',
        breadNumber: 1
      })
    ])
    // Global redirect for 404
  ]
})
router.beforeEach((to, from, next) => {
  let currentUser = ''
  if (global.helper.ls.get('user')) {
    currentUser = global.helper.ls.get('user')
  }
  if (currentUser) {
    // 判断是否登录
    if (currentUser.loginorgid) {
      // 判断是否有组织信息
      if (to.name === 'login') {
        next('/home')
      } else {
        next()
      }
    } else {
      if (to.name === 'improveOrgInfo' || to.name === 'login') {
        next()
      } else {
        next('/improveOrgInfo')
      }
    }
  } else {
    if (to.name !== 'login') {
      if (
        to.name === 'register' ||
        to.name === 'resetPwdSuc' ||
        to.name === 'forgetPwd'
      ) {
        next()
      } else {
        next('/login')
      }
    } else {
      next()
    }
  }
})

export default router
