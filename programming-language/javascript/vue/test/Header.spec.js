import {shallow, createLocalVue} from '@vue/test-utils';
import Header from '../src/components/Header.vue';
import Vuex from 'vuex';

const localVue = createLocalVue();
localVue.use(Vuex)

describe('Header.vue', () => {
  let store;
  let getters;
  let mutations;
  beforeEach(() => {
    getters = {
      session: () => false
    }
    mutations = {
      SET_SESSION: () => {
      }
    }
    store = new Vuex.Store({
      getters,
      mutations
    })
  })

  // test if the props is correct in component
  it('Check prop was sent over correctly to Header', () => {
    const cartItemCount = 10;
    const wrapper = shallow(Header, {
      store, localVue, propsData: {cartItemCount}
    })
    expect(wrapper.vm.cartItemCount).toBe(cartItemCount);
  })

  // test if has text 10 in rendered html
  it('Check cartItemCount text is properly displayed', () => {
    const cartItemCount = 10;
    const wrapper = shallow(Header, {
      store, localVue, propsData: {cartItemCount}
    })
    const p = wrapper.find('span');
    expect(p.text()).toContain(cartItemCount)
  })

  // test if has css class in rendered html
  it('Check if navbar class is added to first div', () => {
    const cartItemCount = 10;
    const wrapper = shallow(Header, {
      store, localVue, propsData: {cartItemCount}
    })
    const p = wrapper.findAll('div').at(0);
    expect(p.classes()).toContain('navbar');
  })

  // // test vuex
  // it('Check if Sign in button text is correct for sign in', () => {
  //   const cartItemCount = 10;
  //   const wrapper = shallow(Header, {
  //     store, localVue, propsData: {cartItemCount}
  //   })
  //
  //   expect(wrapper.findAll('button').at(0).text()).toBe("Sign In");
  //
  // })

  // it('Check if Sign in button text is correct for sign out', () => {
  //   const cartItemCount = 10;
  //   getters.session = () => true;
  //   store = new Vuex.Store({getters, mutations})
  //   const wrapper = shallow(Header, {
  //     store, localVue, propsData: {cartItemCount}
  //   })
  //   debugger;
  //   expect(wrapper.findAll('button').at(0).text()).toBe("Sign Out");
  // })

});
