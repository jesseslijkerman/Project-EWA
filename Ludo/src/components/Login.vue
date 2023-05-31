<template>
  <body class="border-top-wide border-primary d-flex flex-column theme-dark">
  <div class="page page-center">
    <div class="container container-tight py-4">
      <div class="text-center mb-4">
        <a href="." class="navbar-brand"><img src="/assets/Ludo-Logo.jpg" alt="Responsive image"></a>
      </div>
      <div class="card card-md">
        <div class="card-body">
          <h2 class="h2 text-center mb-4">Login to your account</h2>
          <form action="./" method="get" autocomplete="off" novalidate="">
            <div class="mb-3"><label class="form-label">Email address</label> <input type="email" class="form-control"
                                                                                     placeholder="your@email.com"
                                                                                     autocomplete="off"
                                                                                     style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAkCAYAAADo6zjiAAAAAXNSR0IArs4c6QAAAbNJREFUWAntV8FqwkAQnaymUkpChB7tKSfxWCie/Yb+gbdeCqGf0YsQ+hU95QNyDoWCF/HkqdeiIaEUqyZ1ArvodrOHxanQOiCzO28y781skKwFW3scPV1/febP69XqarNeNTB2KGs07U3Ttt/Ozp3bh/u7V7muheQf6ftLUWyYDB5yz1ijuPAub2QRDDunJsdGkAO55KYYjl0OUu1VXOzQZ64Tr+IiPXedGI79bQHdbheCIAD0dUY6gV6vB67rAvo6IxVgWVbFy71KBKkAFaEc2xPQarXA931ot9tyHphiPwpJgSbfe54Hw+EQHMfZ/msVEEURjMfjCjbFeG2dFxPo9/sVOSYzxmAwGIjnTDFRQLMQAjQ5pJAQkCQJ5HlekeERxHEsiE0xUUCzEO9AmqYQhiF0Oh2Yz+ewWCzEY6aYKKBZCAGYs1wuYTabKdNNMWWxnaA4gp3Yry5JBZRlWTXDvaozUgGTyQSyLAP0dbb3DtQlmcan0yngT2ekE9ARc+z4AvC7nauh9iouhpcGamJeX8XF8MaClwaeROWRA7nk+tUnyzGvZrKg0/40gdME/t8EvgG0/NOS6v9NHQAAAABJRU5ErkJggg==&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;">
            </div>
            <div class="mb-2"><label class="form-label">
              Password
              <span class="form-label-description">
                    <a href="./forgot-password.html">I forgot password</a>
                  </span>
            </label>
              <div class="input-group input-group-flat"><input type="password" class="form-control"
                                                               placeholder="Your password" autocomplete="off"
                                                               style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAkCAYAAADo6zjiAAAAAXNSR0IArs4c6QAAAbNJREFUWAntV8FqwkAQnaymUkpChB7tKSfxWCie/Yb+gbdeCqGf0YsQ+hU95QNyDoWCF/HkqdeiIaEUqyZ1ArvodrOHxanQOiCzO28y781skKwFW3scPV1/febP69XqarNeNTB2KGs07U3Ttt/Ozp3bh/u7V7muheQf6ftLUWyYDB5yz1ijuPAub2QRDDunJsdGkAO55KYYjl0OUu1VXOzQZ64Tr+IiPXedGI79bQHdbheCIAD0dUY6gV6vB67rAvo6IxVgWVbFy71KBKkAFaEc2xPQarXA931ot9tyHphiPwpJgSbfe54Hw+EQHMfZ/msVEEURjMfjCjbFeG2dFxPo9/sVOSYzxmAwGIjnTDFRQLMQAjQ5pJAQkCQJ5HlekeERxHEsiE0xUUCzEO9AmqYQhiF0Oh2Yz+ewWCzEY6aYKKBZCAGYs1wuYTabKdNNMWWxnaA4gp3Yry5JBZRlWTXDvaozUgGTyQSyLAP0dbb3DtQlmcan0yngT2ekE9ARc+z4AvC7nauh9iouhpcGamJeX8XF8MaClwaeROWRA7nk+tUnyzGvZrKg0/40gdME/t8EvgG0/NOS6v9NHQAAAABJRU5ErkJggg==&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;">
                <span class="input-group-text">
                    <a href="#" class="link-secondary" data-bs-toggle="tooltip" aria-label="Show password">
                      <svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24"
                           stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round"
                           stroke-linejoin="round"><path stroke="none" d="M0 0h24v24H0z" fill="none"></path><circle
                          cx="12" cy="12" r="2"></circle><path
                          d="M22 12c-2.667 4.667 -6 7 -10 7s-7.333 -2.333 -10 -7c2.667 -4.667 6 -7 10 -7s7.333 2.333 10 7"></path></svg>
                    </a>
                  </span></div>
            </div>
            <div class="mb-2"><label class="form-check">
              <input type="checkbox" class="form-check-input">
              <span class="form-check-label">Remember me on this device</span>
            </label></div>
            <div class="form-footer">
              <button class="btn btn-primary w-100" @click="signIn();">Sign in</button>
            </div>
          </form>
        </div>
      </div>
      <div class="text-center text-muted mt-3"> Don't have account yet? <a href="./sign-up.html" tabindex="-1">Sign
        up</a></div>
    </div>
  </div>
  </body>
</template>

<script>
export default {
  name: 'LoginPageTemp',
  inject: ['userService', 'authenticationService'],


  data() {

    let email = '';
    let password = '';
    let currentToken = this.authenticationService.getTokenFromBrowserStorage();
    let currentUserName = localStorage.getItem('userName');
    let shouldWarnUser = false;
    let warnUser = "These login credentials add up to nothing...";


    return {
      currentToken: currentToken,
      email: email,
      password: password,
      currentUserName: currentUserName,
      warnUser: warnUser,
      shouldWarnUser: shouldWarnUser
    };
  },
  methods: {
    async signIn() {
      await this.authenticationService.asyncSignIn(this.email, this.password);
      location.reload()
    }

  },
}

</script>

<style scoped>
/* Make your page and container take up the entire height and width of the viewport */
.page {
  height: 100vh; /* 100% of the viewport height */
  width: 100vw; /* 100% of the viewport width */
}

.container {
  height: 100%;
  width: 100%;
}

/* Center everything in the page and container */
.page-center {
  display: flex;
  justify-content: center;


}

/* If necessary, constrain the width of the card so it doesn't stretch too wide */
.card-md {
  max-width: 500px; /* Adjust as necessary */
  margin: auto; /* Center the card if the page is wider than the max-width */
}
</style>
