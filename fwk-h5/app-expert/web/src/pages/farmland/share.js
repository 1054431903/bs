import Share from '@/share/index.js'
export default {
  mixins: [Share],
  data () {
    return {
      farmlandDetails: {
        id: undefined,
        name: undefined,
        code: undefined,
        typeCode: undefined,
        typeName: undefined,
        provinceCode: undefined,
        provinceName: undefined,
        cityCode: undefined,
        cityName: undefined,
        countyCode: undefined,
        countyName: undefined,
        townCode: undefined,
        townName: undefined,
        villageCode: undefined,
        villageName: undefined,
        addressCode: undefined,
        addressName: undefined,
        addressDetails: undefined,
        occupiedArea: undefined,
        occupiedAreaUnit: undefined,
        occupiedAreaString: undefined,
        altitude: undefined,
        altitudeUnit: undefined,
        altitudeString: undefined,
        longitude: undefined,
        latitude: undefined,
        coordinateString: undefined,
        belongCode: undefined,
        belongName: undefined,
        insuranceCode: undefined,
        insuranceName: undefined,
        comment: undefined,
        ownerId: undefined,
        ownerName: undefined,
        ownerPhone: undefined,
        principals: [],
        attachments: []
      }
    }
  },
  methods: {
  }
}
