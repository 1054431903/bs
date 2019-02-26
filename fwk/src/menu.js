export default [
  { title: '首页', icon: 'home', to: { name: 'home' } },
  { title: '仪表盘', icon: 'explore', to: { name: 'dashboard' } },
  { title: '地块管理', icon: 'dashboard', to: { name: 'lands' } },
  { title: 'SOP规程', icon: 'settings_brightness', to: { name: 'sop' } },
  { title: '农业知识库', icon: 'book', to: { name: 'repository' } },
  {
    title: '档案管理',
    items: [
      { title: '合作农户档案管理', icon: 'contacts', to: { name: 'farmerarchives' } },
      { title: '合作农户协议管理', icon: 'business', to: { name: 'farmeragreements' } }
    ]
  },
  {
    title: '农事管理',
    items: [
      { title: '农事计划管理', icon: 'event_note', to: { name: 'farmerplans' } },
      { title: '农事任务管理', icon: 'layers', to: { name: 'farmertasks' } },
      { title: '农事记录管理', icon: 'phonelink', to: { name: 'farmerrecords' } }
    ]
  },
  {
    title: '平台管理',
    items: [
      { title: '组织管理', icon: 'business', to: { name: 'organizations' } },
      { title: '成员管理', icon: 'supervisor_account', to: { name: 'MemberManage' } },
      // { title: '角色管理', icon: 'assignment_ind', to: { name: 'roles' } },
      { title: 'SOP发布', icon: 'new_releases', to: { name: 'infopublish' } },
      { title: '字典管理', icon: 'library_books', to: { name: 'sysdictionary' } },
      { title: '角色管理', icon: 'assignment_ind', to: { name: 'roleManage' } },
      { title: '权限管理', icon: 'library_books', to: { name: 'authority' } }
    ]
  }
]
