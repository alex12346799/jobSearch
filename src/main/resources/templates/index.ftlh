





<#import "layout.ftlh" as main>
<#import "spring.ftl" as spring>

<@main.layout>
    <style>
        body {
            background: linear-gradient(180deg, #f8f9ff 0%, #ffffff 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #2d3436;
        }

        .hero {
            text-align: center;
            padding: 80px 20px 50px;
            background: linear-gradient(90deg, #2575fc, #6a11cb);
            color: white;
            border-radius: 0 0 60px 60px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        }
        .hero h1 { font-size: 2.8rem; font-weight: 700; margin-bottom: 16px; }
        .hero p { font-size: 1.15rem; opacity: 0.95; margin-bottom: 26px; }

        .hero-actions {
            display: flex; gap: 14px; justify-content: center; flex-wrap: wrap; margin-top: 10px;
        }
        .btn-hero {
            display: inline-block; padding: 12px 22px; border-radius: 12px; font-weight: 700;
            text-decoration: none; border: 2px solid rgba(255,255,255,0.6);
            background: rgba(255,255,255,0.12); color: #fff;
            transition: transform .15s ease, background .15s ease, border-color .15s ease;
            backdrop-filter: blur(4px);
        }
        .btn-hero:hover { transform: translateY(-2px); background: rgba(255,255,255,0.18); border-color: #fff; }
        .btn-hero.primary { background: #fff; color: #5a31d6; border-color: #fff; }
        .btn-hero.primary:hover { background: #f3f1ff; }

        .stats {
            max-width: 1100px; margin: 24px auto 0; display: grid; grid-template-columns: repeat(3, 1fr);
            gap: 14px; padding: 0 16px 6px;
        }
        .stat-card {
            background: #ffffff; color: #2d3436; border-radius: 14px;
            box-shadow: 0 10px 22px rgba(0,0,0,0.08); padding: 18px 20px; text-align: center;
        }
        .stat-card .num { font-size: 1.6rem; font-weight: 800; margin-bottom: 2px; letter-spacing: .3px; }
        .stat-card .label { font-size: .95rem; color: #5f6b72; }

        .section-title {
            max-width: 1100px; margin: 60px auto 16px; padding: 0 16px;
            font-size: 1.6rem; font-weight: 800; color: #2d3436;
        }
        .section-sub {
            max-width: 1100px; margin: 0 auto 24px; padding: 0 16px; color: #5f6b72;
        }

        .features {
            max-width: 1000px; margin: 30px auto 60px; display: flex; justify-content: space-around;
            flex-wrap: wrap; gap: 30px; padding: 0 16px;
        }
        .feature-card {
            background: #fff; border-radius: 16px; box-shadow: 0 6px 16px rgba(0,0,0,0.08);
            text-align: center; padding: 30px 20px; width: 300px; transition: .25s;
        }
        .feature-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.12); }
        .feature-card img { width: 60px; margin-bottom: 16px; }
        .feature-card h3 { font-size: 1.25rem; color: #2d3436; margin-bottom: 8px; }
        .feature-card p { color: #555; font-size: .95rem; }

        .categories { display:grid; grid-template-columns:repeat(4,1fr); gap:14px; }
        a.cat {
            text-decoration:none; color:inherit; background:#fff; border:1px solid #eef1f6;
            border-radius:12px; padding:14px 16px; display:flex; align-items:center; gap:10px;
            box-shadow:0 6px 16px rgba(0,0,0,.05); transition:transform .15s ease, box-shadow .15s ease;
        }
        a.cat:hover, a.cat:focus-visible { transform: translateY(-2px); box-shadow:0 10px 20px rgba(0,0,0,.10); outline:none; }
        a.cat .badge { width:34px; height:34px; border-radius:10px; display:grid; place-items:center;
            background:#f3f4ff; color:#5a31d6; font-weight:800; flex:0 0 34px; }
        a.cat .title { font-weight:700; }
        a.cat .count { margin-left:auto; color:#6e7b84; font-size:.9rem; }
        @media (max-width:992px){ .categories{ grid-template-columns:repeat(2,1fr);} }
        @media (max-width:640px){ .categories{ grid-template-columns:1fr;} }

        .how {
            max-width: 1100px; margin: 0 auto 10px; padding: 0 16px;
            display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px;
        }
        .step { background: #fff; border-radius: 14px; padding: 18px; box-shadow: 0 8px 18px rgba(0,0,0,.07); }
        .step .n {
            width: 34px; height: 34px; border-radius: 50%; background: #eef0ff; color: #5a31d6;
            display: grid; place-items: center; font-weight: 800; margin-bottom: 10px;
        }
        .step h4 { margin: 0 0 6px; font-size: 1.05rem; }
        .step p { margin: 0; color: #5f6b72; font-size: .95rem; }

        .testimonials {
            max-width: 1100px; margin: 0 auto 10px; padding: 0 16px;
            display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px;
        }
        .tcard { background: #fff; border-radius: 14px; padding: 18px; box-shadow: 0 10px 22px rgba(0,0,0,.07); }
        .tcard .head { display: flex; align-items: center; gap: 12px; margin-bottom: 8px; }
        .avatar {
            width: 38px; height: 38px; border-radius: 50%; display: grid; place-items: center;
            font-weight: 800; color: #fff; background: linear-gradient(135deg,#6a11cb,#2575fc);
        }
        .tcard .name { font-weight: 800; }
        .tcard .role { color: #6e7b84; font-size: .9rem; }
        .tcard p { margin: 8px 0 0; color: #4c5962; font-size: .95rem; }

        .partners {
            max-width: 1100px; margin: 6px auto 70px; padding: 0 16px;
            display: grid; grid-template-columns: repeat(6, 1fr); gap: 12px; align-items: center;
        }
        .logo {
            background: #fff; border: 1px solid #eef1f6; border-radius: 12px; padding: 10px;
            text-align: center; color: #65727b; font-weight: 700; box-shadow: 0 4px 12px rgba(0,0,0,.04);
        }

        @media (max-width: 992px) {
            .categories { grid-template-columns: repeat(2,1fr); }
            .how, .testimonials { grid-template-columns: repeat(2,1fr); }
            .partners { grid-template-columns: repeat(3,1fr); }
        }
        @media (max-width: 640px) {
            .stats { grid-template-columns: 1fr; }
            .categories, .how, .testimonials, .partners { grid-template-columns: 1fr; }
        }
    </style>


    <div class="hero">
        <h1><@spring.message "home.hero.title"/></h1>
        <p><@spring.message "home.hero.subtitle"/></p>

        <div class="hero-actions">
            <a class="btn-hero primary" href="/vacancies"><@spring.message "home.hero.btnVacancies"/></a>
            <a class="btn-hero" href="/resumes/create"><@spring.message "home.hero.btnCreateResume"/></a>
            <a class="btn-hero" href="/vacancies/create"><@spring.message "home.hero.btnPostVacancy"/></a>
        </div>

        <div class="stats">
            <div class="stat-card">
                <div class="num">12 450+</div>
                <div class="label"><@spring.message "home.stats.activeVacancies"/></div>
            </div>
            <div class="stat-card">
                <div class="num">8 900+</div>
                <div class="label"><@spring.message "home.stats.newResumes"/></div>
            </div>
            <div class="stat-card">
                <div class="num">2 100+</div>
                <div class="label"><@spring.message "home.stats.companies"/></div>
            </div>
        </div>
    </div>


    <h2 class="section-title"><@spring.message "home.why.title"/></h2>
    <p class="section-sub"><@spring.message "home.why.subtitle"/></p>
    <div class="features">
        <div class="feature-card">
            <img src="/images/icon-career.svg" alt="career">
            <h3><@spring.message "home.why.seeker.title"/></h3>
            <p><@spring.message "home.why.seeker.text"/></p>
        </div>
        <div class="feature-card">
            <img src="/images/icon-company.svg" alt="company">
            <h3><@spring.message "home.why.employer.title"/></h3>
            <p><@spring.message "home.why.employer.text"/></p>
        </div>
        <div class="feature-card">
            <img src="/images/icon-support.svg" alt="support">
            <h3><@spring.message "home.why.support.title"/></h3>
            <p><@spring.message "home.why.support.text"/></p>
        </div>
    </div>


    <h2 class="section-title"><@spring.message "home.categories.title"/></h2>
    <p class="section-sub"><@spring.message "home.categories.subtitle"/></p>

    <div class="categories">
        <a class="cat" href="/vacancies?category=it" aria-label="<@spring.message 'home.categories.itAria'/>">
            <div class="badge">IT</div><div class="title"><@spring.message "home.categories.it"/></div>
            <div class="count">3 200 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=ds" aria-label="<@spring.message 'home.categories.dsAria'/>">
            <div class="badge">DS</div><div class="title"><@spring.message "home.categories.ds"/></div>
            <div class="count">1 050 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=pm" aria-label="<@spring.message 'home.categories.pmAria'/>">
            <div class="badge">PM</div><div class="title"><@spring.message "home.categories.pm"/></div>
            <div class="count">860 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=mk" aria-label="<@spring.message 'home.categories.mkAria'/>">
            <div class="badge">MK</div><div class="title"><@spring.message "home.categories.mk"/></div>
            <div class="count">1 420 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=dsn" aria-label="<@spring.message 'home.categories.dsnAria'/>">
            <div class="badge">DSN</div><div class="title"><@spring.message "home.categories.dsn"/></div>
            <div class="count">780 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=sl" aria-label="<@spring.message 'home.categories.slAria'/>">
            <div class="badge">SL</div><div class="title"><@spring.message "home.categories.sl"/></div>
            <div class="count">1 960 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=hr" aria-label="<@spring.message 'home.categories.hrAria'/>">
            <div class="badge">HR</div><div class="title"><@spring.message "home.categories.hr"/></div>
            <div class="count">540 <@spring.message "home.categories.vacancies"/></div>
        </a>
        <a class="cat" href="/vacancies?category=fn" aria-label="<@spring.message 'home.categories.fnAria'/>">
            <div class="badge">FN</div><div class="title"><@spring.message "home.categories.fn"/></div>
            <div class="count">690 <@spring.message "home.categories.vacancies"/></div>
        </a>
    </div>


    <h2 class="section-title"><@spring.message "home.how.title"/></h2>
    <p class="section-sub"><@spring.message "home.how.subtitle"/></p>
    <div class="how">
        <div class="step">
            <div class="n">1</div>
            <h4><@spring.message "home.how.step1.title"/></h4>
            <p><@spring.message "home.how.step1.text"/></p>
        </div>
        <div class="step">
            <div class="n">2</div>
            <h4><@spring.message "home.how.step2.title"/></h4>
            <p><@spring.message "home.how.step2.text"/></p>
        </div>
        <div class="step">
            <div class="n">3</div>
            <h4><@spring.message "home.how.step3.title"/></h4>
            <p><@spring.message "home.how.step3.text"/></p>
        </div>
    </div>


    <h2 class="section-title"><@spring.message "home.testimonials.title"/></h2>
    <p class="section-sub"><@spring.message "home.testimonials.subtitle"/></p>
    <div class="testimonials">
        <div class="tcard">
            <div class="head">
                <div class="avatar"><@spring.message "home.testimonials.a1.initials"/></div>
                <div>
                    <div class="name"><@spring.message "home.testimonials.a1.name"/></div>
                    <div class="role"><@spring.message "home.testimonials.a1.role"/></div>
                </div>
            </div>
            <p><@spring.message "home.testimonials.a1.text"/></p>
        </div>
        <div class="tcard">
            <div class="head">
                <div class="avatar"><@spring.message "home.testimonials.b1.initials"/></div>
                <div>
                    <div class="name"><@spring.message "home.testimonials.b1.name"/></div>
                    <div class="role"><@spring.message "home.testimonials.b1.role"/></div>
                </div>
            </div>
            <p><@spring.message "home.testimonials.b1.text"/></p>
        </div>
        <div class="tcard">
            <div class="head">
                <div class="avatar"><@spring.message "home.testimonials.c1.initials"/></div>
                <div>
                    <div class="name"><@spring.message "home.testimonials.c1.name"/></div>
                    <div class="role"><@spring.message "home.testimonials.c1.role"/></div>
                </div>
            </div>
            <p><@spring.message "home.testimonials.c1.text"/></p>
        </div>
    </div>

    <h2 class="section-title"><@spring.message "home.partners.title"/></h2>
    <p class="section-sub"><@spring.message "home.partners.subtitle"/></p>
    <div class="partners">
        <div class="logo"><@spring.message "home.partners.p1"/></div>
        <div class="logo"><@spring.message "home.partners.p2"/></div>
        <div class="logo"><@spring.message "home.partners.p3"/></div>
        <div class="logo"><@spring.message "home.partners.p4"/></div>
        <div class="logo"><@spring.message "home.partners.p5"/></div>
        <div class="logo"><@spring.message "home.partners.p6"/></div>
    </div>
</@main.layout>
