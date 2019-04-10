<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  body,div,dl,dt,dd,ul,li,ol,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,textarea,p,blockquote,th,td{
    margin: 0;
    padding: 0;
  }
  input,select,option,textarea {
    outline: none;
  }
  #app{
    background-color: #fafafa;
  }
  .icon {
    width: 1em; height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
  }
</style>
