<template>
  <v-dialog :value="show"
            @input="close"
            scrollable
            width="1000px">
    <v-card v-if="show">
      <v-toolbar card
                 color="grey lighten-4"
                 height="35px">
        <v-spacer></v-spacer>
        <v-btn icon
               small
               @click.native="close">
          <v-icon>close</v-icon>
        </v-btn>
      </v-toolbar>
      <v-divider></v-divider>
      <v-card-text class="pt-0; text-align:center;">
        <v-carousel hide-delimiters
                    :cycle="false"
                    height="550px">
          <v-carousel-item v-for="(card,index) in this.item.list"
                           :key="index"
                           :src="card.data['v']">
            <v-layout row
                      justify-center>
              <div style="font-size:16px;color:#fff;margin-top:25px;background-color:#000;opacity:0.5;border-radius: 5px;">{{ card.time }}</div>
            </v-layout>
          </v-carousel-item>
        </v-carousel>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from 'moment'
export default {
  name: 'photo-carousel',
  props: {
    show: {
      type: Boolean,
      required: true
    },
    item: { type: Object }
  },
  data () {
    return {
      title: ''
    }
  },
  computed: {
    initShow () {
      return !this.show
    }
  },
  watch: {
    item: function (value) {
      console.log(value)
      value.list.forEach(element => {
        let time = moment(parseInt(element.createdOn.$numberLong)).format('YYYY-MM-DD HH:mm:ss')
        element.time = time
      })
      return value
    }
  },
  methods: {
    close () {
      this.$emit('update:show', this.initShow)
    }
  }
}
</script>
