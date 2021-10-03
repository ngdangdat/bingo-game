<template>
  <div id="app">
    <div id="nav"/>
    <h1>Let's play bingo!</h1>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #ef3939;
  width: 30%;
  margin: 0 auto;
}

a {
  color: #f71313;
  text-decoration: none;
  &:hover {
    font-weight: 600;
  }
}

.cta {
  border: 0px;
  border-radius: .5em;
  padding: 1em;
  text-transform: uppercase;
  font-weight: 700;
  font-size: 1em;
  color: #ffff9f;
  background-color: #ef3939;
  cursor: pointer;
  box-shadow: 1px 1px 2px #999;
}

.noselect {
  -webkit-touch-callout: none; /* iOS Safari */
    -webkit-user-select: none; /* Safari */
     -khtml-user-select: none; /* Konqueror HTML */
       -moz-user-select: none; /* Old versions of Firefox */
        -ms-user-select: none; /* Internet Explorer/Edge */
            user-select: none; /* Non-prefixed version, currently
                                  supported by Chrome, Edge, Opera and Firefox */
}

.buttons {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0 auto;
  margin-bottom: 1em;
  button {
    flex: 1;
    margin-right: 1em;
    &:last-child {
      margin-right: 0;
    }
  }
}

</style>
